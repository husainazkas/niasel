package middleware

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/database/models"
	"github.com/husainazkas/go_playground/src/helpers"
)

func abortBadConfig(ctx *gin.Context) {
	ctx.AbortWithStatusJSON(500, helpers.ErrorResponse("Server cannot retrieve user data from Auth Token. This may occur due to bad configuration."))
}

func abortNotPermit(ctx *gin.Context) {
	ctx.AbortWithStatusJSON(403, helpers.ErrorResponse("You are not permitted to process this request"))
}

func CreateUpdateProductPermission(ctx *gin.Context) {
	user, _ := ctx.Get("user")
	if user == nil {
		abortBadConfig(ctx)
		return
	}

	_user := user.(models.User)
	if !_user.Role.CreateUpdateProduct {
		abortNotPermit(ctx)
		return
	}

	ctx.Next()
}

func DeleteProductPermission(ctx *gin.Context) {
	user, _ := ctx.Get("user")
	if user == nil {
		abortBadConfig(ctx)
		return
	}

	_user := user.(models.User)
	if !_user.Role.DeleteProduct {
		abortNotPermit(ctx)
		return
	}

	ctx.Next()
}
