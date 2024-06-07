package product

import (
	"strconv"

	"github.com/husainazkas/go_playground/src/database/models"
	"github.com/husainazkas/go_playground/src/helpers/pagination"
)

func getListProductService(query *pagination.PaginationSchema) (*pagination.Pagination, error) {
	var result pagination.Pagination

	if err := find(&result, query); err != nil {
		return nil, err
	}

	return &result, nil
}

func GetDetailProductService(productId string) (*models.Product, error) {
	var product models.Product

	if err := findOne(&product, productId); err != nil {
		return nil, err
	}

	return &product, nil
}

func saveProductService(body *productSchema, productId string, user models.User) error {
	product := &models.Product{
		BarcodeId: body.BarcodeId,
		Name:      body.Name,
		Price:     body.Price,
		Stock:     body.Stock,
		Brand:     body.Brand,
		TimestampsAuthor: models.TimestampsAuthor{
			UpdatedBy: user.Id,
		},
	}

	if productId == "" {
		product.TimestampsAuthor.CreatedBy = user.Id
	} else {
		id64, _ := strconv.ParseUint(productId, 10, 0)
		id := uint(id64)

		product.Id = &id
	}

	return saveProduct(product)
}

func softDeleteProductService(productId string, user models.User) error {
	id64, _ := strconv.ParseUint(productId, 10, 0)
	id := uint(id64)

	return softDeleteProduct(id, *user.Id)
}
