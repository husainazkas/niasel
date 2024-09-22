package order_status

import (
	"strconv"

	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers/pagination"
)

func getListOrderStatusService(query *pagination.PaginationSchema) (*pagination.Pagination, error) {
	var result pagination.Pagination

	if err := find(&result, query); err != nil {
		return nil, err
	}

	return &result, nil
}

func saveOrderStatusService(body *orderStatusSchema, orderStatusId string, user models.User) error {
	orderStatus := &models.OrderStatus{
		Name:  body.Name,
		Value: body.Value,
		TimestampsAuthor: models.TimestampsAuthor{
			UpdatedBy: user.Id,
		},
	}

	if orderStatusId == "" {
		orderStatus.TimestampsAuthor.CreatedBy = user.Id
	} else {
		id64, _ := strconv.ParseUint(orderStatusId, 10, 0)
		id := uint(id64)

		orderStatus.Id = &id
	}

	return save(orderStatus)
}

func deleteService(orderStatusId string) error {
	id64, _ := strconv.ParseUint(orderStatusId, 10, 0)
	id := uint(id64)

	return delete(id)
}
