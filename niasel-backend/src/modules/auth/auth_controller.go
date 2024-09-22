package auth

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers"
)

func handleLogin(ctx *gin.Context) {
	var body loginSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	data, token, err := loginService(&body, ctx.ClientIP())

	if err != nil {
		ctx.AbortWithStatusJSON(401, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Login successfully", helpers.Data{"token": token, "user": data}))
}
