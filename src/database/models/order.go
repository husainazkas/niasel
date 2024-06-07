package models

import "time"

type Order struct {
	Id         *uint      `gorm:"type:bigint;primaryKey" json:"id"`
	Uuid       string     `gorm:"type:varchar(36)" json:"uuid"`
	CartId     uint       `json:"cart_id"`
	Cash       uint       `gorm:"type:int(11);not null" json:"cash"`
	CashChange uint       `gorm:"type:int(11);not null" json:"cash_change"`
	BankId     *uint      `json:"bank_id"`
	BankTrxRef string     `json:"bank_trx_ref"`
	StatusId   uint       `json:"status_id"`
	CreatedAt  *time.Time `gorm:"<-:create" json:"created_at"`
	CreatedBy  *uint      `gorm:"type:bigint;<-:create" json:"created_by"`

	// Relations
	Bank   *Bank       `gorm:"foreignKey:BankId" json:"bank,omitempty"`
	Status OrderStatus `gorm:"foreignKey:StatusId" json:"status,omitempty"`
	Cart   Cart        `gorm:"foreignKey:CartId" json:"cart,omitempty"`
}

// Overrides the table name
func (Order) TableName() string {
	return "sales_order"
}
