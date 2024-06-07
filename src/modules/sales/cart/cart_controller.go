package cart

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/database/models"
	"github.com/husainazkas/go_playground/src/helpers"
)

func handleDetailCart(ctx *gin.Context) {
	data, err := getCartByIdService(ctx.Param("id"))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Item added successfully", helpers.Data{"data": data}))
}

func handleNewCart(ctx *gin.Context) {
	var body newCartSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	data, err := newCartService(&body, user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(201, helpers.SuccessResponse("New cart created successfully", helpers.Data{"data": data}))
}

func handleAddItemToCart(ctx *gin.Context) {
	var body cartItemSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	data, err := updateCartItemService(true, &body, ctx.Param("id"), user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Item added successfully", helpers.Data{"data": data}))
}

func handleRemoveItemToCart(ctx *gin.Context) {
	var body cartItemSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	data, err := updateCartItemService(false, &body, ctx.Param("id"), user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Item removed successfully", helpers.Data{"data": data}))
}
