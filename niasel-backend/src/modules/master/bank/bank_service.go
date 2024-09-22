package bank

import (
	"strconv"

	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers/pagination"
)

func getListBankService(query *pagination.PaginationSchema) (*pagination.Pagination, error) {
	var result pagination.Pagination

	if err := find(&result, query); err != nil {
		return nil, err
	}

	return &result, nil
}

func saveBankService(body *bankSchema, orderStatusId string, user models.User) error {
	bank := &models.Bank{
		Name: body.Name,
		Code: body.Code,
		TimestampsAuthor: models.TimestampsAuthor{
			UpdatedBy: user.Id,
		},
	}

	if orderStatusId == "" {
		bank.TimestampsAuthor.CreatedBy = user.Id
	} else {
		id64, _ := strconv.ParseUint(orderStatusId, 10, 0)
		id := uint(id64)

		bank.Id = &id
	}

	return save(bank)
}

func deleteService(orderStatusId string) error {
	id64, _ := strconv.ParseUint(orderStatusId, 10, 0)
	id := uint(id64)

	return delete(id)
}
