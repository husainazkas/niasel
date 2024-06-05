package auth

import (
	"github.com/husainazkas/go_playground/src/config"
	"github.com/husainazkas/go_playground/src/database/models"
)

func findByUsername(user *models.User, username string) error {
	return config.DB.Preload("Account").
		Where("account_id = (?)", config.DB.Model(&models.Account{}).Select("id").Where("username = ?", username)).
		First(&user).Error
}

func saveSession(session *models.Session) error {
	return config.DB.Save(session).Error
}
