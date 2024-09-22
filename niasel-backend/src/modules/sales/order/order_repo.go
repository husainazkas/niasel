package order

import (
	"github.com/husainazkas/niasel/niasel-backend/src/config"
	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
)

func findOne(id string, order *models.Order) error {
	return config.DB.Preload("Cart").Where("id = ?", id).First(order).Error
}

func verifyUniqueCart(cartId uint) (bool, error) {
	var count int64
	if err := config.DB.Model(&models.Order{}).Where("cart_id = ?", cartId).Count(&count).Error; err != nil {
		return false, err
	}
	return count == 0, nil
}

func save(value any) error {
	return config.DB.Save(value).Error
}

func cancelOrder(order *models.Order, userId uint) error {
	if err := config.DB.Where("status_id = ?", order.StatusId).First(order).Error; err != nil {
		return err
	}
	return config.DB.Model(order).Updates(map[string]any{
		"status_id":  3,
		"updated_by": userId,
	}).Error
}
