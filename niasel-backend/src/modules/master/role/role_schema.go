package role

type roleSchema struct {
	Name                     string `binding:"required,alpha" json:"name"`
	CreateUpdateDeleteMaster bool   `json:"c_u_d_master"`
	CreateUpdateProduct      bool   `json:"c_u_product"`
	DeleteProduct            bool   `json:"d_product"`
	ReadUsers                bool   `json:"r_users"`
	CreateUpdateUser         bool   `json:"c_u_user"`
	DeleteUser               bool   `json:"d_user"`
	CreatePurchase           bool   `json:"c_purchase"`
	IsActive                 bool   `json:"is_active"`
}
