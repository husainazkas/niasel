package models

import "github.com/husainazkas/go_playground/src/types"

type Account struct {
	Id        *uint         `gorm:"type:bigint;primaryKey" json:"id"`
	Username  string        `gorm:"not null" json:"-"`
	Password  *string       `gorm:"not null" json:"-"`
	IsActive  types.BitBool `gorm:"not null" json:"is_active"`
	IsDeleted types.BitBool `gorm:"not null" json:"is_deleted"`
	TimestampsAuthor
}

// Overrides the table name
func (Account) TableName() string {
	return "secure_account"
}
