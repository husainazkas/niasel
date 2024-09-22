package user

import (
	"errors"
	"strconv"

	"github.com/husainazkas/niasel/niasel-backend/src/database/models"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers"
	"github.com/husainazkas/niasel/niasel-backend/src/helpers/pagination"
	"github.com/husainazkas/niasel/niasel-backend/src/types"
	"golang.org/x/crypto/bcrypt"
)

func getListUserService(query *pagination.PaginationSchema) (*pagination.Pagination, error) {
	var result pagination.Pagination

	if err := find(&result, query); err != nil {
		return nil, err
	}

	return &result, nil
}

func getUserDetailService(userId string) (*models.User, error) {
	var user models.User

	if err := fineOne(&user, userId); err != nil {
		return nil, err
	}

	return &user, nil
}

func saveUserService(body *userSchema, userId string, user models.User) error {
	_user := &models.User{
		FirstName: body.FirstName,
		LastName:  body.LastName,
		RoleId:    uint(body.RoleId),
		TimestampsAuthor: models.TimestampsAuthor{
			UpdatedBy: user.Id,
		},
	}

	if userId == "" {
		// ---------- Create new user ----------

		if body.Username == "" || body.Password == "" {
			return errors.New("username and password are required")
		}

		pass, err := bcrypt.GenerateFromPassword([]byte(body.Password), 0) // cost will set to default
		if err != nil {
			return err
		}

		password := string(pass)
		_user.Account.Username = helpers.SHA1HexFromString(body.Username)
		_user.Account.Password = &password
		_user.Account.IsActive = types.BitBool(body.IsActive)
		_user.Account.TimestampsAuthor.UpdatedBy = user.Id
		_user.Account.TimestampsAuthor.CreatedBy = user.Id
		_user.TimestampsAuthor.CreatedBy = user.Id
	} else {
		// ---------- Update existed user ----------

		existed := &models.User{}
		if err := fineOne(existed, userId); err != nil {
			return err
		}

		if *existed.Id == *user.Id && !body.IsActive {
			return errors.New("you cannot disable yourself")
		}

		if body.NewPassword != "" {
			// ---------- Update password account ----------

			if err := bcrypt.CompareHashAndPassword([]byte(*existed.Account.Password), []byte(body.OldPassword)); err != nil {
				return errors.New("old password is wrong")
			}

			pass, err := bcrypt.GenerateFromPassword([]byte(body.NewPassword), 0) // cost will set to default
			if err != nil {
				return err
			}

			password := string(pass)
			account := &models.Account{
				Id:       &existed.AccountId,
				Password: &password,
			}

			if err := updatePasswordAccount(account, *user.Id); err != nil {
				return err
			}
		}

		account := &models.Account{
			Id:       &existed.AccountId,
			IsActive: types.BitBool(body.IsActive),
		}
		if err := setActiveAccount(account, *user.Id); err != nil {
			return err
		}

		id64, _ := strconv.ParseUint(userId, 10, 0)
		id := uint(id64)

		_user.Id = &id
		_user.AccountId = existed.AccountId
	}

	return save(_user)
}

func softDeleteUserService(userId string, user models.User) error {
	id64, _ := strconv.ParseUint(userId, 10, 0)
	id := uint(id64)

	if id == *user.Id {
		return errors.New("you cannot delete yourself")
	}

	return softDeleteUserAccount(id, *user.Id)
}
