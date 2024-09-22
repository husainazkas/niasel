package order

import (
	"errors"
	"strconv"

	"github.com/google/uuid"
	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
	"github.com/husainazkas/niasel/niasel-backend/src/modules/sales/cart"
	"gorm.io/gorm"
)

func createOrderService(body *orderSchema, user models.User) (*models.Order, error) {
	cart, err := cart.GetCartByIdService(strconv.Itoa(int(body.CartId)), nil)
	if err != nil {
		return nil, err
	}

	passed, err := verifyUniqueCart(*cart.Id)
	if err != nil {
		return nil, err
	} else if !passed {
		return nil, errors.New("this cart is already made an order")
	}

	uuidV7, err := uuid.NewV7()
	if err != nil {
		return nil, err
	}

	order := models.Order{
		Uuid:   uuidV7.String(),
		CartId: body.CartId,
		TimestampsAuthor: models.TimestampsAuthor{
			UpdatedBy: user.Id,
			CreatedBy: user.Id,
		},
	}

	if body.Payment != nil {
		if err := proceedPaymentService(&order, *body.Payment, cart.TotalPrice); err != nil {
			return nil, err
		}
	} else {
		order.StatusId = 1
	}

	if err := save(&order); err != nil {
		return nil, err
	}

	return &order, nil
}

func payOrderService(body *paymentSchema, orderId string, user models.User) (*models.Order, error) {
	order := models.Order{}

	if err := findOne(orderId, &order); err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, errors.New("order not found")
		}
		return nil, err
	}

	switch order.StatusId {
	case 2:
		return nil, errors.New("order is already succeeded, you cannot pay for twice")
	case 3:
		return nil, errors.New("order is already canceled, you have to create a new one")
	case 4:
		return nil, errors.New("order is already failed, you have to create a new one")
	}

	isShouldOrdered := true
	cart, err := cart.GetCartByIdService(strconv.Itoa(int(order.CartId)), &isShouldOrdered)
	if err != nil {
		return nil, err
	}

	if err := proceedPaymentService(&order, *body, cart.TotalPrice); err != nil {
		return nil, err
	}

	order.StatusId = 2
	order.TimestampsAuthor.UpdatedBy = user.Id

	if err := save(&order); err != nil {
		return nil, err
	}

	return &order, nil
}

func proceedPaymentService(order *models.Order, body paymentSchema, totalPrice uint) error {
	bankId, bankRef := body.BankId, body.BankTrxRef
	if (bankId != nil && bankRef == nil) || (bankId == nil && bankRef != nil) {
		return errors.New("bank_id and bank_trx_ref must be filled or null at the same time")
	}

	cashChange := int(body.Cash - totalPrice)
	if cashChange < 0 {
		return errors.New("your cash is not enough to proceed the payment")
	}

	change := uint(cashChange)
	order.Cash = &body.Cash
	order.CashChange = &change
	order.BankId = body.BankId
	order.BankTrxRef = body.BankTrxRef
	order.StatusId = 2

	return nil
}

func cancelOrderService(orderId string, user models.User) error {
	id, err := strconv.Atoi(orderId)
	if err != nil {
		return err
	}

	orderid := uint(id)
	if err := cancelOrder(&models.Order{Id: &orderid, StatusId: 1}, *user.Id); err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return errors.New("order not found or has closed")
		}
		return err
	}
	return nil
}
