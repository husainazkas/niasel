package order_status

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/middleware"
)

func Routes(router *gin.RouterGroup) {
	authorized := router.Group("/", middleware.Auth)
	authorized.GET("/", handleListOrderStatus)
	authorized.POST("/add", middleware.CreateUpdateDeleteMaster, handleAddOrderStatus)
	authorized.PUT("/update/:id", middleware.CreateUpdateDeleteMaster, handleUpdateOrderStatus)
	authorized.DELETE("/delete/:id", middleware.CreateUpdateDeleteMaster, handleDeleteOrderStatus)
}
