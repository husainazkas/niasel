package product

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers/pagination"
)

func handleListProduct(ctx *gin.Context) {
	var query pagination.PaginationSchema

	ctx.ShouldBindQuery(&query)

	data, err := getListProductService(&query)
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, data)
}

func handleAddProduct(ctx *gin.Context) {
	var body productSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	err := saveProductService(&body, "", user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(201, helpers.SuccessResponse("Product added successfully"))
}

func handleUpdateProduct(ctx *gin.Context) {
	var body productSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	err := saveProductService(&body, ctx.Param("id"), user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Product updated successfully"))
}

func handleDeleteProduct(ctx *gin.Context) {
	user, _ := ctx.Get("user")
	err := softDeleteProductService(ctx.Param("id"), user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Product deleted successfully"))
}
