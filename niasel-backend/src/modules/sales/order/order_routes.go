package order

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/middleware"
)

func Routes(router *gin.RouterGroup) {
	order := router.Group("/", middleware.CreatePurchasePermission)
	order.POST("/create", handleCreateOrder)
	order.PUT("/pay/:id", handlePaymentOrder)
	order.PUT("/cancel/:id", handleCancelOrder)
}
