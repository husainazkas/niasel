package product

type productSchema struct {
	BarcodeId string  `binding:"required" json:"barcode_id"`
	Name      string  `binding:"required" mod:"trim" json:"name"`
	Stock     int     `binding:"required,min=0" json:"stock"`
	Price     int     `binding:"required,min=0" json:"price"`
	Brand     *string `mod:"trim" json:"brand"`
}
