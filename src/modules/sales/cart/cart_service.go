package cart

import (
	"errors"
	"slices"
	"strconv"

	"github.com/husainazkas/go_playground/src/database/models"
	"github.com/husainazkas/go_playground/src/modules/master/product"
	"gorm.io/gorm"
)

func getCartByIdService(cartId string) (*models.Cart, error) {
	cart := models.Cart{}
	if err := findCart(cartId, &cart); err != nil {
		return nil, err
	}
	return &cart, nil
}

func newCartService(body *newCartSchema, user models.User) (*models.Cart, error) {
	cart := models.Cart{}

	for _, v := range body.Items {
		product, err := product.GetDetailProductService(strconv.Itoa(int(v.Id)))
		if err != nil {
			return nil, err
		}

		item := models.CartItem{
			ProductId: product.Id,
			Count:     v.Count,
			Price:     v.Count * product.Price,
		}
		cart.Items = append(cart.Items, item)
		cart.TotalItem += item.Count
		cart.TotalPrice += item.Price
	}

	cart.TimestampsAuthor.UpdatedBy = user.Id
	cart.TimestampsAuthor.CreatedBy = user.Id

	if err := save(&cart); err != nil {
		return nil, err
	}

	return &cart, nil
}

func updateCartItemService(isAdding bool, body *cartItemSchema, cartId string, user models.User) (*models.Cart, error) {
	cart := &models.Cart{}
	if err := findCart(cartId, cart); err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, errors.New("cart not found")
		}
		return nil, err
	}

	cart.TotalItem = 0
	cart.TotalPrice = 0

	isNewItem := true
	removedItems := []uint{}
	for i, v := range cart.Items {
		if *v.ProductId == body.Id {
			isNewItem = false
			product, err := product.GetDetailProductService(strconv.Itoa(int(body.Id)))
			if err != nil {
				return nil, err
			}

			if isAdding {
				v.Count += body.Count
				v.Price += body.Count * product.Price
			} else {
				count := int(v.Count - body.Count)
				if count <= 0 {
					if err := removeCartItem(&v); err != nil {
						return nil, err
					}
					removedItems = append(removedItems, *v.Id)
					continue
				}
				v.Count -= body.Count
				v.Price -= body.Count * product.Price
			}

			if err := save(v); err != nil {
				return nil, err
			}
		}
		cart.Items[i] = v
		cart.TotalItem += v.Count
		cart.TotalPrice += v.Price
	}

	if isNewItem {
		if isAdding {
			product, err := product.GetDetailProductService(strconv.Itoa(int(body.Id)))
			if err != nil {
				return nil, err
			}

			item := models.CartItem{
				ProductId: product.Id,
				Count:     body.Count,
				Price:     body.Count * product.Price,
			}
			cart.Items = append(cart.Items, item)
			cart.TotalItem += item.Count
			cart.TotalPrice += item.Price
		} else {
			return nil, errors.New("nothing can be removed")
		}
	}

	for _, v := range removedItems {
		cart.Items = slices.DeleteFunc(cart.Items, func(e models.CartItem) bool {
			return *e.Id == v
		})
	}

	cart.UpdatedBy = user.Id

	if err := save(cart); err != nil {
		return nil, err
	}

	return cart, nil
}
