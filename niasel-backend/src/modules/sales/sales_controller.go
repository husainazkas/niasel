package sales

import (
	"github.com/gin-gonic/gin"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers"
)

func handleSalesSummaries(ctx *gin.Context) {
	var query salesSchema

	ctx.ShouldBindQuery(&query)

	data, err := getSalesSummariesService(&query)
	if err != nil {
		ctx.AbortWithStatusJSON(400, helpers.ErrorResponse(err.Error()))
		return
	}

	ctx.JSON(200, data)
}
