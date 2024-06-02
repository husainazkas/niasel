package main

import (
	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"github.com/gin-gonic/gin/binding"
	"github.com/husainazkas/go_playground/src/config"
	"github.com/husainazkas/go_playground/src/helpers"
	"github.com/husainazkas/go_playground/src/helpers/session"
	"github.com/husainazkas/go_playground/src/middleware"
	"github.com/husainazkas/go_playground/src/modules/auth"
	"github.com/husainazkas/go_playground/src/modules/product"
)

func init() {
	config.LoadEnvVariables()
	config.ConnectDB()
	if e := session.InitSign(); e != nil {
		panic(e)
	}
}

func main() {
	router := gin.Default()

	router.Use(cors.Default())
	router.Use(middleware.RateLimiter)

	customValidator := helpers.NewCustomValidator()
	binding.Validator = customValidator

	router.Static("/public", "./public")
	router.Static("/.well-known", "./.well-known")

	// routes
	auth.Routes(router.Group("/api/auth"))
	product.Routes(router.Group("/api/product"))

	router.NoRoute(func(c *gin.Context) {
		c.JSON(404, helpers.ErrorResponse("No route found"))
	})

	router.Run()
}
