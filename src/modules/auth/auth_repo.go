package auth

import (
	"github.com/husainazkas/go_playground/src/config"
	"github.com/husainazkas/go_playground/src/database/models"
)

func findByUsername(user *models.User, username string) error {
	return config.DB.Preload("Account").
		Joins("JOIN secure_account ON secure_account.id = master_user.account_id AND secure_account.username = ?", username).
		First(&user).Error
}

func saveSession(session *models.Session) error {
	return config.DB.Save(session).Error
}
