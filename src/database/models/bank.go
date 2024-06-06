package models

type Bank struct {
	Id   *uint  `gorm:"type:bigint;primaryKey" json:"id"`
	Name string `json:"name"`
	Code string `gorm:"not null" json:"code"`
	TimestampsAuthor
}

// Overrides the table name
func (Bank) TableName() string {
	return "master_bank"
}
