package auth

type loginSchema struct {
	Username string `binding:"required" mod:"trim" json:"username"`
	Password string `binding:"required" json:"password"`
	DeviceId string `binding:"required" json:"device_id"`
}