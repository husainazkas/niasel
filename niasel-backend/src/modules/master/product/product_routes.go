package product

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/niasel/niasel-backend/src/middleware"
)

func Routes(router *gin.RouterGroup) {
	authorized := router.Group("/", middleware.Auth)
	authorized.GET("/", handleListProduct)
	authorized.POST("/add", middleware.CreateUpdateProductPermission, handleAddProduct)
	authorized.PUT("/update/:id", middleware.CreateUpdateProductPermission, handleUpdateProduct)
	authorized.DELETE("/delete/:id", middleware.DeleteProductPermission, handleDeleteProduct)
}
