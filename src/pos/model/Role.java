/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @Column(name = "is_active", insertable = false, columnDefinition = "BIT", length = 1)
    private Boolean isActive;

    @Column(name = "is_deleted", insertable = false, columnDefinition = "BIT", length = 1)
    private Boolean isDeleted;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Date updatedAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt;

    @Column(name = "created_by", updatable = false)
    private Long createdBy;

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

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (name != null ? name.hashCode() : 0);
        hash += (isActive != null ? isActive.hashCode() : 0);
        hash += (isDeleted != null ? isDeleted.hashCode() : 0);
        hash += (updatedAt != null ? updatedAt.hashCode() : 0);
        hash += (updatedBy != null ? updatedBy.hashCode() : 0);
        hash += (createdAt != null ? createdAt.hashCode() : 0);
        hash += (createdBy != null ? createdBy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        if ((this.isActive == null && other.isActive != null) || (this.isActive != null && !this.isActive.equals(other.isActive))) {
            return false;
        }
        if ((this.isDeleted == null && other.isDeleted != null) || (this.isDeleted != null && !this.isDeleted.equals(other.isDeleted))) {
            return false;
        }
        if ((this.updatedAt == null && other.updatedAt != null) || (this.updatedAt != null && !this.updatedAt.equals(other.updatedAt))) {
            return false;
        }
        if ((this.updatedBy == null && other.updatedBy != null) || (this.updatedBy != null && !this.updatedBy.equals(other.updatedBy))) {
            return false;
        }
        if ((this.createdAt == null && other.createdAt != null) || (this.createdAt != null && !this.createdAt.equals(other.createdAt))) {
            return false;
        }
        return !((this.createdBy == null && other.createdBy != null) || (this.createdBy != null && !this.createdBy.equals(other.createdBy)));
    }

    @Override
    public String toString() {
        return "pos.model.Role[ id=" + id + ", name=" + name + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + ", createdAt=" + createdAt + ", createdBy=" + createdBy + " ]";
    }

}
