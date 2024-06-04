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

type roleSchema struct {
	Name                string `binding:"required,alpha" json:"name"`
	CreateUpdateProduct bool   `json:"c_u_product"`
	DeleteProduct       bool   `json:"d_product"`
	ReadUsers           bool   `json:"r_users"`
	CreateUpdateUser    bool   `json:"c_u_user"`
	DeleteUser          bool   `json:"d_user"`
	CreatePurchase      bool   `json:"c_purchase"`
	IsActive            bool   `json:"is_active"`
}
