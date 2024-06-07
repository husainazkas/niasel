package sales

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/middleware"
	"github.com/husainazkas/go_playground/src/modules/sales/cart"
	"github.com/husainazkas/go_playground/src/modules/sales/order"
)

func Routes(router *gin.RouterGroup) {
	authorized := router.Group("/", middleware.Auth)
	authorized.GET("/summary", handleSalesSummaries)

	cart.Routes(authorized.Group("/cart"))
	order.Routes(authorized.Group("/order"))
}
