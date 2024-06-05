package role

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/middleware"
)

func Routes(router *gin.RouterGroup) {
	authorized := router.Group("/", middleware.Auth)
	authorized.GET("/", middleware.CreateUpdateUserPermission, handleListRole)
	authorized.POST("/add", middleware.AllUserRolePermission, handleAddRole)
	authorized.PUT("/update/:id", middleware.CreateUpdateUserPermission, handleUpdateRole)
	authorized.DELETE("/delete/:id", middleware.AllUserRolePermission, handleDeleteRole)
}
