package sales

import (
	"time"

	"github.com/husainazkas/niasel/niasel-backend/src/helpers/pagination"
)

type salesSchema struct {
	pagination.PaginationSchema
	StartDate *time.Time `form:"start_date" binding:"lte" time_format:"2006-01-02" time_utc:"1"`
	EndDate   *time.Time `form:"end_date" binding:"gtefield=StartDate,lte" time_format:"2006-01-02" time_utc:"1"`
}
