package order

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/database/models"
	"github.com/husainazkas/go_playground/src/helpers"
)

func handleCreateOrder(ctx *gin.Context) {
	var body orderSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	data, err := createOrderService(&body, user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(201, helpers.SuccessResponse("Order created successfully", helpers.Data{"data": data}))
}

func handlePaymentOrder(ctx *gin.Context) {
	var body paymentSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	data, err := payOrderService(&body, ctx.Param("id"), user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Order payment successfully", helpers.Data{"data": data}))
}

func handleCancelOrder(ctx *gin.Context) {
	user, _ := ctx.Get("user")
	if err := cancelOrderService(ctx.Param("id"), user.(models.User)); err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Order canceled successfully"))
}
