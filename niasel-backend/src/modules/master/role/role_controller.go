package role

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers/pagination"
)

func handleListRole(ctx *gin.Context) {
	var query pagination.PaginationSchema

	ctx.ShouldBindQuery(&query)

	data, err := getListRoleService(&query)

	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, data)
}

func handleAddRole(ctx *gin.Context) {
	var body roleSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	err := saveRoleService(&body, "", user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(201, helpers.SuccessResponse("Role added successfully"))
}

func handleUpdateRole(ctx *gin.Context) {
	var body roleSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	err := saveRoleService(&body, ctx.Param("id"), user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Role updated successfully"))
}

func handleDeleteRole(ctx *gin.Context) {
	err := deleteRoleService(ctx.Param("id"))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Role deleted successfully"))
}
