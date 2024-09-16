package role

import (
	"github.com/husainazkas/go_playground/src/config"
	"github.com/husainazkas/go_playground/src/database/models"
	"github.com/husainazkas/go_playground/src/helpers/pagination"
)

func find(result *pagination.Pagination, query *pagination.PaginationSchema) error {
	q := config.DB.Where("name LIKE ?", "%"+query.Search+"%")
	return result.New(&pagination.Params{
		Query:     q,
		Model:     &[]models.Role{},
		Page:      query.Page,
		Limit:     query.Limit,
		Order:     query.Order,
		Direction: query.Direction,
	})
}

func saveRole(role *models.Role) error {
	return config.DB.Save(role).Error
}

func deleteRole(id uint) error {
	return config.DB.Delete(models.Role{Id: &id}).Error
}
