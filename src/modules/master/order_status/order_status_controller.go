package order_status

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/database/models"
	"github.com/husainazkas/go_playground/src/helpers"
	"github.com/husainazkas/go_playground/src/helpers/pagination"
)

func handleListOrderStatus(ctx *gin.Context) {
	var query pagination.PaginationSchema

	ctx.ShouldBindQuery(&query)

	data, err := getListOrderStatusService(&query)
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, data)
}

func handleAddOrderStatus(ctx *gin.Context) {
	var body orderStatusSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	err := saveOrderStatusService(&body, "", user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(201, helpers.SuccessResponse("Order status added successfully"))
}

func handleUpdateOrderStatus(ctx *gin.Context) {
	var body orderStatusSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	err := saveOrderStatusService(&body, ctx.Param("id"), user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Order status updated successfully"))
}

func handleDeleteOrderStatus(ctx *gin.Context) {
	err := deleteService(ctx.Param("id"))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Order status deleted successfully"))
}
