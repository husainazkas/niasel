package bank

type bankSchema struct {
	Name string `binding:"required" json:"name"`
	Code string `binding:"required,numeric" json:"code"`
}
