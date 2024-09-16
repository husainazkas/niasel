package helpers

import (
	"regexp"
	"strings"
)

var matchFirstCap = regexp.MustCompile("(.)([A-Z][a-z]+)")
var matchAllCap = regexp.MustCompile("([a-z0-9])([A-Z])")

func ToSnakeCase(str string) string {
	snake := matchFirstCap.ReplaceAllString(str, "${1}_${2}")
	snake = matchAllCap.ReplaceAllString(snake, "${1}_${2}")
	return strings.ToLower(snake)
}

func ToUpperFirstChar(str string) string {
	return strings.ToUpper(string(str[0])) + str[1:]
}

func ToUpperFirstCharEachWord(str string) string {
	return strings.Join(
		Map(strings.Split(str, " "), func(e string) string {
			return ToUpperFirstChar(e)
		}),
		" ",
	)
}

func AddWhiteSpaceByUpperCase(str string) string {
	return strings.Join(regexp.MustCompile(`[A-Z][^A-Z]*`).FindAllString(str, -1), " ")
}
