package user

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/niasel/niasel-backend/src/middleware"
)

func Routes(router *gin.RouterGroup) {
	authorized := router.Group("/", middleware.Auth)
	authorized.GET("/", middleware.ReadUsersPermission, handleListUser)
	authorized.GET("/:id", middleware.ReadUsersPermission, handleDetailUser)
	authorized.POST("/add", middleware.CreateUpdateUserPermission, handleAddUser)
	authorized.PUT("/update/:id", middleware.CreateUpdateUserPermission, handleUpdateUser)
	authorized.DELETE("/delete/:id", middleware.DeleteUserPermission, handleDeleteUser)
}
