package role

import (
	"strconv"

	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers/pagination"
	"github.com/husainazkas/niasel/niasel-backend/src/types"
)

func getListRoleService(query *pagination.PaginationSchema) (*pagination.Pagination, error) {
	var result pagination.Pagination

	if err := find(&result, query); err != nil {
		return nil, err
	}

	return &result, nil
}

func saveRoleService(body *roleSchema, roleId string, user models.User) error {
	role := &models.Role{
		Name:                     body.Name,
		CreateUpdateDeleteMaster: types.BitBool(body.CreateUpdateDeleteMaster),
		CreateUpdateProduct:      types.BitBool(body.CreateUpdateProduct),
		DeleteProduct:            types.BitBool(body.DeleteProduct),
		ReadUsers:                types.BitBool(body.ReadUsers),
		CreateUpdateUser:         types.BitBool(body.CreateUpdateUser),
		DeleteUser:               types.BitBool(body.DeleteUser),
		CreatePurchase:           types.BitBool(body.CreatePurchase),
		IsActive:                 types.BitBool(body.IsActive),
		TimestampsAuthor: models.TimestampsAuthor{
			UpdatedBy: user.Id,
		},
	}

	if roleId == "" {
		role.TimestampsAuthor.CreatedBy = user.Id
	} else {
		id64, _ := strconv.ParseUint(roleId, 10, 0)
		id := uint(id64)

		role.Id = &id
	}

	return saveRole(role)
}

func deleteRoleService(userId string) error {
	id64, _ := strconv.ParseUint(userId, 10, 0)
	id := uint(id64)

	return deleteRole(id)
}
