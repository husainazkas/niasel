package middleware

import (
	"errors"
	"strings"

	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/config"
	"github.com/husainazkas/go_playground/src/database/models"
	"github.com/husainazkas/go_playground/src/helpers"
	"github.com/husainazkas/go_playground/src/helpers/session"
	"gorm.io/gorm"
)

func Auth(ctx *gin.Context) {
	token := ctx.Request.Header.Get("Authorization")

	if token == "" {
		ctx.AbortWithStatusJSON(401, helpers.ErrorResponse("You are not authorized!"))
		return
	}

	token = strings.Split(token, " ")[1]

	claim, err := session.Parse(token)
	if err != nil {
		ctx.AbortWithStatusJSON(401, helpers.ErrorResponse(err.Error()))
		return
	}

	if claim["type"].(string) != "access" {
		ctx.AbortWithStatusJSON(401, helpers.ErrorResponse("Invalid token."))
		return
	}

	var session models.Session
	if err := config.DB.Where("access_token = ?", token).Preload("Account").First(&session).Error; err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			ctx.AbortWithStatusJSON(401, helpers.ErrorResponse("Invalid token."))
		} else {
			ctx.AbortWithStatusJSON(500, helpers.ErrorResponse(err.Error()))
		}
		return
	}

	var user models.User
	if err := config.DB.Where("account_id = ?", session.AccountId).Preload("Role").First(&user).Error; err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			ctx.AbortWithStatusJSON(401, helpers.ErrorResponse("User not found."))
		} else {
			ctx.AbortWithStatusJSON(500, helpers.ErrorResponse(err.Error()))
		}
		return
	}

	if session.Account.IsDeleted {
		ctx.AbortWithStatusJSON(401, helpers.ErrorResponse("Account has been deleted. Please contact administrator."))
		return
	}

	if !session.Account.IsActive {
		ctx.AbortWithStatusJSON(403, helpers.ErrorResponse("Account has been disabled. Please contact administrator."))
		return
	}

	if uint(claim["role"].(float64)) != user.RoleId {
		ctx.AbortWithStatusJSON(403, helpers.ErrorResponse("You have no permission to process this request."))
		return
	}

	ctx.Set("user", user)

	ctx.Next()
}
