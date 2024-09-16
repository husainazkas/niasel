package models

import "github.com/husainazkas/go_playground/src/types"

type Product struct {
	Id        *uint         `gorm:"type:bigint;primaryKey" json:"id"`
	BarcodeId string        `gorm:"type:varchar(12);not null" json:"barcode_id"`
	Name      string        `gorm:"type:varchar(64);not null" json:"name"`
	Price     uint          `gorm:"type:int(11);not null" json:"price"`
	Stock     uint          `gorm:"type:int(11);not null" json:"stock"`
	Brand     *string       `gorm:"type:varchar(64)" json:"brand"`
	IsDeleted types.BitBool `gorm:"not null" json:"is_deleted"`
	TimestampsAuthor
}

// Overrides the table name
func (Product) TableName() string {
	return "master_product"
}
