package sales

import "github.com/husainazkas/go_playground/src/helpers/pagination"

func getSalesSummariesService(query *salesSchema) (*pagination.Pagination, error) {
	var result pagination.Pagination

	if err := find(&result, query); err != nil {
		return nil, err
	}

	return &result, nil
}
