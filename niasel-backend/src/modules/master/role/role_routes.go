package role

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/middleware"
)

func Routes(router *gin.RouterGroup) {
	authorized := router.Group("/", middleware.Auth)
	authorized.GET("/", middleware.ReadUsersPermission, handleListRole)
	authorized.POST(
		"/add",
		middleware.CreateUpdateDeleteMaster,
		middleware.AllUserRolePermission,
		handleAddRole,
	)
	authorized.PUT(
		"/update/:id",
		middleware.CreateUpdateDeleteMaster,
		middleware.CreateUpdateUserPermission,
		handleUpdateRole,
	)
	authorized.DELETE(
		"/delete/:id",
		middleware.CreateUpdateDeleteMaster,
		middleware.AllUserRolePermission,
		handleDeleteRole,
	)
}
