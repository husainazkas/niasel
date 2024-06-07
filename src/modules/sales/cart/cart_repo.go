package cart

import (
	"errors"

	"github.com/husainazkas/go_playground/src/config"
	"github.com/husainazkas/go_playground/src/database/models"
)

func findCart(id string, cart *models.Cart) error {
	return config.DB.Preload("Items").
		Where("id = ?", id).
		Where("id NOT IN (?)", config.DB.Model(&models.Order{}).Select("cart_id")).
		First(cart).Error
}

func save(value any) error {
	return config.DB.Save(value).Error
}

func removeCartItem(cartItem *models.CartItem) error {
	if cartItem.Count > 1 {
		return errors.New("count of this item is more than one, are you sure to remove all of this item at once?")
	}
	return config.DB.Delete(cartItem).Error
}
