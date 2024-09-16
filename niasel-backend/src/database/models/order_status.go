package models

type OrderStatus struct {
	Id    *uint  `gorm:"type:bigint;primaryKey" json:"id"`
	Name  string `json:"name"`
	Value uint   `gorm:"type:tinyint(1);not null" json:"value"`
	TimestampsAuthor
}

// Overrides the table name
func (OrderStatus) TableName() string {
	return "master_order_status"
}
