package order_status

import (
	"github.com/husainazkas/go_playground/src/config"
	"github.com/husainazkas/go_playground/src/database/models"
	"github.com/husainazkas/go_playground/src/helpers/pagination"
)

func find(result *pagination.Pagination, query *pagination.PaginationSchema) error {
	q := config.DB.
		Where("name LIKE ?", "%"+query.Search+"%")

	return result.New(&pagination.Params{
		Query:     q,
		Model:     &[]models.OrderStatus{},
		Page:      query.Page,
		Limit:     query.Limit,
		Order:     query.Order,
		Direction: query.Direction,
	})
}

func save(orderStatus *models.OrderStatus) error {
	return config.DB.Save(orderStatus).Error
}

func delete(id uint) error {
	return config.DB.Delete(&models.OrderStatus{Id: &id}).Error
}
