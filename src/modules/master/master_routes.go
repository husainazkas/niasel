package master

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/go_playground/src/modules/master/product"
	"github.com/husainazkas/go_playground/src/modules/master/role"
)

func Routes(router *gin.RouterGroup) {
	product.Routes(router.Group("/product"))
	role.Routes(router.Group("/role"))
}
