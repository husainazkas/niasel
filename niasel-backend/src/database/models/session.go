package models

import "time"

type Session struct {
	Id           *uint     `gorm:"type:bigint;primaryKey" json:"-"`
	AccessToken  string    `gorm:"not null" json:"access_token"`
	RefreshToken string    `gorm:"not null" json:"refresh_token"`
	AccountId    *uint     `json:"-"`
	DeviceId     *string   `json:"-"`
	Ip4          *string   `gorm:"column:ip_address" json:"-"`
	ExpiredAt    time.Time `json:"-"`
	CreatedAt    time.Time `json:"-"`

	// Relations
	Account *Account `gorm:"foreignKey:AccountId" json:"-"`
}

// Overrides the table name
func (Session) TableName() string {
	return "auth_session"
}
