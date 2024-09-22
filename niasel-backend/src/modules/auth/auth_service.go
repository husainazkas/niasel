package auth

import (
	"errors"

	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers/session"
	"golang.org/x/crypto/bcrypt"
)

func loginService(body *loginSchema, ipAddr string) (*models.User, *models.Session, error) {
	var user models.User

	if err := findByUsername(&user, helpers.SHA1HexFromString(body.Username)); err != nil {
		return nil, nil, errors.New("invalid username or password")
	}

	if err := bcrypt.CompareHashAndPassword([]byte(*user.Account.Password), []byte(body.Password)); err != nil {
		return nil, nil, errors.New("invalid username or password")
	}

	userSession := session.New(user.AccountId, user.RoleId, ipAddr, &body.DeviceId)

	if err := saveSession(&userSession); err != nil {
		return nil, nil, err
	}

	user.Account = nil

	return &user, &userSession, nil
}
