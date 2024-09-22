package cart

import (
	"errors"

	"github.com/husainazkas/niasel/niasel-backend/src/config"
	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
)

func findCart(id string, cart *models.Cart, isShouldOrdered *bool) error {
	query := config.DB.Preload("Items").Where("id = ?", id)

	if isShouldOrdered != nil {
		q := config.DB.Model(&models.Order{}).Select("cart_id")
		if *isShouldOrdered {
			query = query.Where("id IN (?)", q)
		} else {
			query = query.Where("id NOT IN (?)", q)
		}
	}

	return query.First(cart).Error
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
