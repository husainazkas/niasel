package helpers

import (
	"github.com/gin-gonic/gin"
)

func Bind(ctx *gin.Context, model any) *gin.H {
	if err := ctx.ShouldBind(model); err != nil {
		parsed, parseErr := ParseError(err)

		if parseErr == nil {
			data := make([]Data, 0, len(parsed))
			for _, v := range parsed {
				data = append(data, Data{v.Field: v.Msg})
			}
			return ErrorResponse("your request didn't pass validation", data...)
		} else {
			return ErrorResponse(err.Error())
		}
	}

	return nil
}
