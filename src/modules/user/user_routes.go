package user

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/middleware"
)

func Routes(router *gin.RouterGroup) {
	authorized := router.Group("/", middleware.Auth)
	authorized.GET("/:id", middleware.ReadUsersPermission, handleDetailUser)

	authorized.GET("/list-user", middleware.ReadUsersPermission, handleListUser)
	authorized.GET("/list-role", middleware.CreateUpdateUserPermission, handleListRole)

	authorized.POST("/add-user", middleware.CreateUpdateUserPermission, handleAddUser)
	authorized.POST("/add-role", middleware.AllUserRolePermission, handleAddRole)

	authorized.PUT("/update-user/:id", middleware.CreateUpdateUserPermission, handleUpdateUser)
	authorized.PUT("/update-role/:id", middleware.CreateUpdateUserPermission, handleUpdateRole)

	authorized.DELETE("/delete-user/:id", middleware.DeleteUserPermission, handleDeleteUser)
	authorized.DELETE("/delete-role/:id", middleware.AllUserRolePermission, handleDeleteRole)
}
