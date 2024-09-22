package user

import (
	"github.com/husainazkas/niasel/niasel-backend/src/config"
	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers/pagination"
)

func find(result *pagination.Pagination, query *pagination.PaginationSchema) error {
	keyword := "%" + query.Search + "%"
	q := config.DB.
		Where("first_name LIKE ? OR last_name LIKE ?", keyword, keyword).
		Where("account_id IN (?)", config.DB.Model(&models.Account{}).Select("id").Where("is_deleted = 0"))

	return result.New(&pagination.Params{
		Query:     q,
		Model:     &[]models.User{},
		Page:      query.Page,
		Limit:     query.Limit,
		Order:     query.Order,
		Direction: query.Direction,
	})
}

func fineOne(user *models.User, id string) error {
	return config.DB.Preload("Role").Preload("Account").Where("id = ?", id).First(user).Error
}

func save(user *models.User) error {
	return config.DB.Save(user).Error
}

func setActiveAccount(account *models.Account, userId uint) error {
	return config.DB.Model(account).Updates(map[string]any{
		"is_active":  account.IsActive,
		"updated_by": userId,
	}).Error
}

func updatePasswordAccount(account *models.Account, userId uint) error {
	return config.DB.Model(account).Updates(map[string]any{
		"password":   account.Password,
		"updated_by": userId,
	}).Error
}

func softDeleteUserAccount(id uint, userId uint) error {
	return config.DB.Model(models.Account{Id: &id}).Updates(map[string]any{
		"is_deleted": 1,
		"updated_by": userId,
	}).Error
}
