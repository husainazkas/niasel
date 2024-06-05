package product

import (
	"github.com/husainazkas/go_playground/src/config"
	"github.com/husainazkas/go_playground/src/database/models"
	"github.com/husainazkas/go_playground/src/helpers/pagination"
)

func find(result *pagination.Pagination, query *pagination.PaginationSchema) error {
	keyword := "%" + query.Search + "%"
	q := config.DB.
		Where("barcode_id = ? OR name LIKE ? OR brand LIKE ?", keyword, keyword, keyword).
		Where("is_deleted = 0")

	return result.New(&pagination.Params{
		Query:     q,
		Model:     &[]models.Product{},
		Page:      query.Page,
		Limit:     query.Limit,
		Order:     query.Order,
		Direction: query.Direction,
	})
}

func saveProduct(product *models.Product) error {
	return config.DB.Save(product).Error
}

func softDeleteProduct(id uint, userId uint) error {
	return config.DB.Model(models.Product{Id: &id}).Updates(map[string]any{
		"is_deleted": 1,
		"updated_by": userId,
	}).Error
}
