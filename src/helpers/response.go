package helpers

import (
	"github.com/gin-gonic/gin"
)

type Data map[string]any

func ErrorResponse(errMsg any, data ...Data) *gin.H {
	response := gin.H{
		"status": "Error",
	}

	setResponseMessage(response, errMsg)
	setResponseData(response, data)

	return &response
}

func SuccessResponse(msg any, data ...Data) *gin.H {
	response := gin.H{
		"status": "Success",
	}

	setResponseMessage(response, msg)
	setResponseData(response, data)

	return &response
}

func setResponseMessage(response gin.H, msg any) {
	if v, ok := msg.(string); ok {
		msg = ToUpperFirstChar(v)
	}

	response["message"] = msg
}

func setResponseData(response gin.H, data []Data) {
	if len(data) > 0 {
		d := Data{}
		if len(data[0]) > 1 {
			for _, e := range data {
				for key, value := range e {
					d[key] = value
				}
			}
			response["data"] = d
		} else {
			for _, value := range data[0] {
				response["data"] = value
			}
		}
	}
}
