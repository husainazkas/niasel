package sales

import (
	"github.com/husainazkas/go_playground/src/config"
	"github.com/husainazkas/go_playground/src/database/models"
	"github.com/husainazkas/go_playground/src/helpers/pagination"
)

func find(result *pagination.Pagination, query *salesSchema) error {
	q := config.DB

	if query.Search != "" {
		keyword := "%" + query.Search + "%"
		q = q.Where("uuid = ? OR bank_trx_ref LIKE ?", keyword, keyword)
	}

	if sd := query.StartDate; sd != nil {
		q = q.Where("created_at >= ?", sd)
	}

	if ed := query.EndDate; ed != nil {
		q = q.Where("created_at <= ?", ed)
	}

	return result.New(&pagination.Params{
		Query:     q,
		Model:     &[]models.Order{},
		Page:      query.PaginationSchema.Page,
		Limit:     query.PaginationSchema.Limit,
		Order:     query.PaginationSchema.Order,
		Direction: query.PaginationSchema.Direction,
	})
}
