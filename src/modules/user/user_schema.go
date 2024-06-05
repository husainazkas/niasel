package user

type userSchema struct {
	FirstName string `binding:"required,alpha" json:"first_name"`
	LastName  string `binding:"required,alpha" json:"last_name"`
	RoleId    int    `binding:"required" json:"role_id"`
	IsActive  bool   `json:"is_active"`

	// Ignored when update
	Username string `mod:"trim" json:"username"`

	// Ignored when update
	Password string `json:"password"`

	// Ignored when create
	OldPassword string `json:"old_password"`

	// Ignored when create
	NewPassword string `json:"new_password"`
}
