package middleware

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers"
	"golang.org/x/time/rate"
)

// rate (20/s) and burst (30/s)
var limiter = rate.NewLimiter(rate.Limit(20), 30)

func RateLimiter(ctx *gin.Context) {
	if !limiter.Allow() {
		ctx.AbortWithStatusJSON(429, helpers.ErrorResponse("Too Many Request"))
	}
}
