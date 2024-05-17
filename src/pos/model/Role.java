/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author husainazkas
 */
@Entity
@Table(name = "master_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column()
    private String name;

    @Column(name = "c_u_product", insertable = false, columnDefinition = "BIT", length = 1)
    private Boolean isCanUpdateProduct;

    @Column(name = "r_users", insertable = false, columnDefinition = "BIT", length = 1)
    private Boolean isCanReadUsers;

    @Column(name = "c_u_user", insertable = false, columnDefinition = "BIT", length = 1)
    private Boolean isCanCreateUpdateUser;

    @Column(name = "d_user", insertable = false, columnDefinition = "BIT", length = 1)
    private Boolean isCanDeleteUser;

    @Column(name = "c_purchase", insertable = false, columnDefinition = "BIT", length = 1)
    private Boolean isCanCreatePurchase;

    @Column(name = "is_active", insertable = false, columnDefinition = "BIT", length = 1)
    private Boolean isActive;

    @Column(name = "is_deleted", insertable = false, columnDefinition = "BIT", length = 1)
    private Boolean isDeleted;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "created_by", updatable = false)
    private User createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsCanUpdateProduct(boolean isCanUpdateProduct) {
        this.isCanUpdateProduct = isCanUpdateProduct;
    }

    public boolean getIsCanUpdateProduct() {
        return isCanUpdateProduct;
    }

    public void setIsCanReadUsers(boolean isCanReadUsers) {
        this.isCanReadUsers = isCanReadUsers;
    }

    public boolean getIsCanReadUsers() {
        return isCanReadUsers;
    }

    public void setIsCanCreateUpdateUser(boolean isCanCreateUpdateUser) {
        this.isCanCreateUpdateUser = isCanCreateUpdateUser;
    }

    public boolean getIsCanCreateUpdateUser() {
        return isCanCreateUpdateUser;
    }

    public void setIsCanDeleteUser(boolean isCanDeleteUser) {
        this.isCanDeleteUser = isCanDeleteUser;
    }

    public boolean getIsCanDeleteUser() {
        return isCanDeleteUser;
    }

    public void setIsCanCreatePurchase(boolean isCanCreatePurchase) {
        this.isCanCreatePurchase = isCanCreatePurchase;
    }

    public boolean getIsCanCreatePurchase() {
        return isCanCreatePurchase;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += Objects.hashCode(id);
        hash += Objects.hashCode(name);
        hash += Objects.hashCode(isCanUpdateProduct);
        hash += Objects.hashCode(isCanReadUsers);
        hash += Objects.hashCode(isCanCreateUpdateUser);
        hash += Objects.hashCode(isCanDeleteUser);
        hash += Objects.hashCode(isCanCreatePurchase);
        hash += Objects.hashCode(isActive);
        hash += Objects.hashCode(isDeleted);
        hash += Objects.hashCode(updatedAt);
        hash += Objects.hashCode(updatedBy);
        hash += Objects.hashCode(createdAt);
        hash += Objects.hashCode(createdBy);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.isCanUpdateProduct, other.isCanUpdateProduct)) {
            return false;
        }
        if (!Objects.equals(this.isCanReadUsers, other.isCanReadUsers)) {
            return false;
        }
        if (!Objects.equals(this.isCanCreateUpdateUser, other.isCanCreateUpdateUser)) {
            return false;
        }
        if (!Objects.equals(this.isCanDeleteUser, other.isCanDeleteUser)) {
            return false;
        }
        if (!Objects.equals(this.isCanCreatePurchase, other.isCanCreatePurchase)) {
            return false;
        }
        if (!Objects.equals(this.isActive, other.isActive)) {
            return false;
        }
        if (!Objects.equals(this.isDeleted, other.isDeleted)) {
            return false;
        }
        if (!Objects.equals(this.updatedAt, other.updatedAt)) {
            return false;
        }
        if (!Objects.equals(this.updatedBy, other.updatedBy)) {
            return false;
        }
        if (!Objects.equals(this.createdAt, other.createdAt)) {
            return false;
        }
        return Objects.equals(this.createdBy, other.createdBy);
    }

    @Override
    public String toString() {
        return "pos.model.Role[ id=" + id + ", name=" + name + ", isCanUpdateProduct=" + isCanUpdateProduct + ", isCanReadUsers=" + isCanReadUsers + ", isCanCreateUpdateUser=" + isCanCreateUpdateUser + ", isCanDeleteUser=" + isCanDeleteUser + ", isCanCreatePurchase=" + isCanCreatePurchase + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + ", createdAt=" + createdAt + ", createdBy=" + createdBy + " ]";
    }

}
