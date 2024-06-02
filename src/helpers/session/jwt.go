package session

import (
	"crypto/rsa"
	"fmt"
	"os"

	"github.com/golang-jwt/jwt/v5"
)

var key *rsa.PrivateKey

func InitSign() error {
	dat, err := os.ReadFile("private_key")
	if err != nil {
		return err
	}

	rsa, err := jwt.ParseRSAPrivateKeyFromPEM(dat)
	if err != nil {
		return err
	}

	key = rsa

	return nil
}

func Parse(token string) (jwt.MapClaims, error) {
	parsedToken, err := jwt.Parse(token, func(token *jwt.Token) (any, error) {
		// Don't forget to validate the alg is what you expect and verify token:
		if a, ok := token.Method.(*jwt.SigningMethodRSA); ok {
			sstr, err := token.SigningString()
			if err != nil {
				return nil, err
			}

			if err := a.Verify(sstr, token.Signature, &key.PublicKey); err != nil {
				return nil, err
			}

			return &key.PublicKey, nil
		}

		// Another signing method will never success.
		return nil, fmt.Errorf("unexpected signing method: %v", token.Header["alg"])
	})

	if err != nil {
		return nil, err
	}

	claim, _ := parsedToken.Claims.(jwt.MapClaims)
	return claim, err
}

func new(claim jwt.MapClaims) string {
	token := jwt.NewWithClaims(jwt.SigningMethodRS256, claim)
	tokenString, _ := token.SignedString(key)

	return tokenString
}
