package sales

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/middleware"
	"github.com/husainazkas/go_playground/src/modules/sales/cart"
)

func Routes(router *gin.RouterGroup) {
	authorized := router.Group("/", middleware.Auth)

	cart.Routes(authorized.Group("/cart"))
}
