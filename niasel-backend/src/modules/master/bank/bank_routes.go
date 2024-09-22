package bank

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/niasel/niasel-backend/src/middleware"
)

func Routes(router *gin.RouterGroup) {
	authorized := router.Group("/", middleware.Auth)
	authorized.GET("/", handleListBank)
	authorized.POST("/add", middleware.CreateUpdateDeleteMaster, handleAddBank)
	authorized.PUT("/update/:id", middleware.CreateUpdateDeleteMaster, handleUpdateBank)
	authorized.DELETE("/delete/:id", middleware.CreateUpdateDeleteMaster, handleDeleteBank)
}
