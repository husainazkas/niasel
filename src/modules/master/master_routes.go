package master

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/modules/master/bank"
	"github.com/husainazkas/go_playground/src/modules/master/order_status"
	"github.com/husainazkas/go_playground/src/modules/master/product"
	"github.com/husainazkas/go_playground/src/modules/master/role"
)

func Routes(router *gin.RouterGroup) {
	bank.Routes(router.Group("/bank"))
	order_status.Routes(router.Group("/order-status"))
	product.Routes(router.Group("/product"))
	role.Routes(router.Group("/role"))
}
