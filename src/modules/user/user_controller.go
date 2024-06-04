package user

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/database/models"
	"github.com/husainazkas/go_playground/src/helpers"
	"github.com/husainazkas/go_playground/src/helpers/pagination"
)

func handleDetailUser(ctx *gin.Context) {
	data, err := getUserDetailService(ctx.Param("id"))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("User updated successfully", helpers.Data{"user": data}))
}

func handleListUser(ctx *gin.Context) {
	var query pagination.PaginationSchema

	ctx.ShouldBindQuery(&query)

	data, err := getListUserService(&query)

	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, data)
}

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

func handleAddUser(ctx *gin.Context) {
	var body userSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	err := saveUserService(&body, "", user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(201, helpers.SuccessResponse("User added successfully"))
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

func handleUpdateUser(ctx *gin.Context) {
	var body userSchema

	if err := helpers.Bind(ctx, &body); err != nil {
		ctx.AbortWithStatusJSON(400, err)
		return
	}

	user, _ := ctx.Get("user")
	err := saveUserService(&body, ctx.Param("id"), user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("User updated successfully"))
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

func handleDeleteUser(ctx *gin.Context) {
	user, _ := ctx.Get("user")
	err := softDeleteUserService(ctx.Param("id"), user.(models.User))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("User deleted successfully"))
}

func handleDeleteRole(ctx *gin.Context) {
	err := deleteRoleService(ctx.Param("id"))
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, helpers.SuccessResponse("Role deleted successfully"))
}
