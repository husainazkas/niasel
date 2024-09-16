package cart

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/middleware"
)

func Routes(router *gin.RouterGroup) {
	cart := router.Group("/", middleware.CreatePurchasePermission)
	cart.GET("/:id", handleDetailCart)
	cart.POST("/new", handleNewCart)
	cart.PUT("/add-item/:id", handleAddItemToCart)
	cart.PUT("/remove-item/:id", handleRemoveItemToCart)
}
