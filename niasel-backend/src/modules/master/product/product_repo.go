package product

import (
	"github.com/husainazkas/niasel/niasel-backend/src/config"
	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers/pagination"
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

func findOne(product *models.Product, id string) error {
	return config.DB.Where("id = ?", id).Find(product).Error
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
