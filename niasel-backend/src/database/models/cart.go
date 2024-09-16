package models

type Cart struct {
	Id         *uint `gorm:"type:bigint;primaryKey" json:"id"`
	TotalItem  uint  `gorm:"type:int(4);not null" json:"total_item"`
	TotalPrice uint  `gorm:"type:int(11);not null" json:"total_price"`
	TimestampsAuthor

	Items []CartItem `gorm:"foreignKey:CartId" json:"items,omitempty"`
}

// Overrides the table name
func (Cart) TableName() string {
	return "sales_cart"
}
