package order

type orderSchema struct {
	CartId  uint           `binding:"required" json:"cart_id"`
	Payment *paymentSchema `json:"payment"`
}

type paymentSchema struct {
	Cash       uint    `binding:"required" json:"cash"`
	BankId     *uint   `json:"bank_id"`
	BankTrxRef *string `json:"bank_trx_ref"`
}
