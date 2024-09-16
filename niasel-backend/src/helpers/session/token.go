package session

import (
	"os"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/husainazkas/go_playground/src/database/models"
)

func New(accountId uint, roleId uint, ipAddr string, deviceId *string) models.Session {
	var session models.Session
	createdAt := time.Now()
	expiredAt := time.Now().Add(time.Hour)

	session.AccessToken = new(jwt.MapClaims{
		"iss":  os.Getenv("BASE_URL"),
		"sub":  accountId,
		"iat":  float64(createdAt.Unix()),
		"exp":  float64(expiredAt.Unix()),
		"type": "access",
		"role": roleId,
	})

	session.RefreshToken = new(jwt.MapClaims{
		"iss":  os.Getenv("BASE_URL"),
		"sub":  accountId,
		"iat":  float64(createdAt.Unix()),
		"exp":  float64(time.Now().Add(time.Hour * 24 * 5).Unix()),
		"type": "refresh",
	})

	session.AccountId = &accountId
	session.Ip4 = &ipAddr
	session.DeviceId = deviceId
	session.ExpiredAt = expiredAt
	session.CreatedAt = createdAt

	return session
}
