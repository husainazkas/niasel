package order_status

type orderStatusSchema struct {
	Name  string `binding:"required" json:"name"`
	Value uint   `binding:"required,min=0" json:"value"`
}
