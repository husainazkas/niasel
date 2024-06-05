package models

type User struct {
	Id        *uint  `gorm:"type:bigint;primaryKey" json:"id"`
	FirstName string `json:"first_name"`
	LastName  string `json:"last_name"`
	RoleId    uint   `json:"role_id"`
	AccountId uint   `json:"account_id"`
	TimestampsAuthor

	// Relations
	Role    *Role    `gorm:"foreignKey:RoleId" json:"role,omitempty"`
	Account *Account `gorm:"foreignKey:AccountId" json:"account,omitempty"`
}

// Overrides the table name
func (User) TableName() string {
	return "user_user"
}
