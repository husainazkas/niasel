package helpers

import (
	"errors"

	"github.com/go-playground/validator/v10"
	"github.com/husainazkas/go_playground/src/config"
)

type ApiError struct {
	Field string `json:"field"`
	Msg   string `json:"message"`
}

func ParseError(err error) ([]ApiError, error) {
	var ve validator.ValidationErrors

	if errors.As(err, &ve) {
		out := make([]ApiError, len(ve))
		for i, fe := range ve {
			out[i] = ApiError{
				Field: ToSnakeCase(fe.Field()),
				Msg:   config.MsgForTag(fe.Tag(), fe.Field(), fe.Param()),
			}
		}
		return out, nil
	}

	return nil, err
}
