package config

func MsgForTag(tag string, field string, param string) string {
	switch tag {
	case "required":
		return field + " must be filled"
	case "username":
		return "Invalid username"
	case "min":
		return field + " length cannot be less than " + param + " characters"
	case "max":
		return field + " length cannot be more than " + param + " characters"
	case "len":
		return field + " length must be equal to " + param + " characters"
	case "unique":
		return field + " is already used"
	case "numeric":
		return field + " must be a number"
	}
	return ""
}
