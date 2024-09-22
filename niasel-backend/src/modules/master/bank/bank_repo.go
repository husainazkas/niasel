package bank

import (
	"github.com/husainazkas/niasel/niasel-backend/src/config"
	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers/pagination"
)

func find(result *pagination.Pagination, query *pagination.PaginationSchema) error {
	keyword := "%" + query.Search + "%"
	q := config.DB.
		Where("name LIKE ? OR code LIKE ?", keyword, keyword)

	return result.New(&pagination.Params{
		Query:     q,
		Model:     &[]models.Bank{},
		Page:      query.Page,
		Limit:     query.Limit,
		Order:     query.Order,
		Direction: query.Direction,
	})
}

func save(bank *models.Bank) error {
	return config.DB.Save(bank).Error
}

func delete(id uint) error {
	return config.DB.Delete(&models.Bank{Id: &id}).Error
}
