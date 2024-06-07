package models

type CartItem struct {
	Id        *uint `gorm:"type:bigint;primaryKey" json:"id"`
	CartId    uint  `gorm:"type:bigint;not null" json:"cart_id"`
	ProductId *uint `gorm:"type:bigint" json:"product_id"`
	Count     uint  `gorm:"type:int(4);not null" json:"count"`
	Price     uint  `gorm:"type:int(11);not null" json:"price"`
}

// Overrides the table name
func (CartItem) TableName() string {
	return "sales_cart_items"
}
