package main

import (
	"os"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"github.com/gin-gonic/gin/binding"
	"github.com/husainazkas/niasel/niasel-backend/src/config"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers/session"
	"github.com/husainazkas/niasel/niasel-backend/src/middleware"
	"github.com/husainazkas/niasel/niasel-backend/src/modules/auth"
	"github.com/husainazkas/niasel/niasel-backend/src/modules/master"
	"github.com/husainazkas/niasel/niasel-backend/src/modules/sales"
	"github.com/husainazkas/niasel/niasel-backend/src/modules/user"
)

func init() {
	config.LoadEnvVariables()
	config.ConnectDB()
	if e := session.InitSign(); e != nil {
		panic(e)
	}
	gin.SetMode(os.Getenv(gin.EnvGinMode))
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
	auth.Routes(router.Group("/api/v1/auth"))
	master.Routes(router.Group("/api/v1/master"))
	sales.Routes(router.Group("/api/v1/sales"))
	user.Routes(router.Group("/api/v1/user"))

	router.NoRoute(func(c *gin.Context) {
		c.JSON(404, helpers.ErrorResponse("No route found"))
	})

	router.Run()
}
