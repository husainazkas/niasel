package cart

type newCartSchema struct {
	Items []cartItemSchema `binding:"required" json:"items"`
}

type cartItemSchema struct {
	Id    uint `binding:"required" json:"id"`
	Count uint `binding:"required" json:"count"`
}
