package models

import (
	"time"
)

type Id struct {
	Id *uint `gorm:"type:bigint;primaryKey" json:"id"`
}

type Timestamps struct {
	UpdatedAt *time.Time `json:"updated_at"`
	CreatedAt *time.Time `gorm:"<-:create" json:"created_at"`
}
type TimestampsAuthor struct {
	Timestamps
	UpdatedBy *uint `gorm:"type:bigint" json:"updated_by"`
	CreatedBy *uint `gorm:"type:bigint;<-:create" json:"created_by"`
}
