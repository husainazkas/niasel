package models

import "github.com/husainazkas/go_playground/src/types"

type Role struct {
	Id                  *uint         `gorm:"type:bigint;primaryKey" json:"id"`
	Name                string        `gorm:"not null" json:"name"`
	CreateUpdateProduct types.BitBool `gorm:"column:c_u_product;not null" json:"c_u_product"`
	DeleteProduct       types.BitBool `gorm:"column:d_product;not null" json:"d_product"`
	ReadUsers           types.BitBool `gorm:"column:r_users;not null" json:"r_users"`
	CreateUpdateUser    types.BitBool `gorm:"column:c_u_user;not null" json:"c_u_user"`
	DeleteUser          types.BitBool `gorm:"column:d_user;not null" json:"d_user"`
	CreatePurchase      types.BitBool `gorm:"column:c_purchase;not null" json:"c_purchase"`
	IsActive            types.BitBool `gorm:"not null" json:"is_active"`
	TimestampsAuthor
}

// Overrides the table name
func (Role) TableName() string {
	return "master_role"
}
