/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author husainazkas
 */
@Entity
@Table(name = "sales_order")
public class SalesOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    @Column(name = "total_price")
    private Long totalPrice;

    private Long cash;

    @Column(name = "cash_change")
    private Long cashChange;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getCash() {
        return cash;
    }

    public void setCash(Long cash) {
        this.cash = cash;
    }

    public Long getCashChange() {
        return cashChange;
    }

    public void setCashChange(Long cashChange) {
        this.cashChange = cashChange;
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
        hash += Objects.hashCode(uuid);
        hash += Objects.hashCode(totalPrice);
        hash += Objects.hashCode(cash);
        hash += Objects.hashCode(cashChange);
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
        final SalesOrder other = (SalesOrder) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.uuid, other.uuid)) {
            return false;
        }
        if (!Objects.equals(this.cash, other.cash)) {
            return false;
        }
        if (!Objects.equals(this.cashChange, other.cashChange)) {
            return false;
        }
        if (!Objects.equals(this.totalPrice, other.totalPrice)) {
            return false;
        }
        if (!Objects.equals(this.createdAt, other.createdAt)) {
            return false;
        }
        return Objects.equals(this.createdBy, other.createdBy);
    }

    @Override
    public String toString() {
        return "pos.model.SalesOrder[ id=" + id + ", uuid=" + uuid + ", totalPrice=" + totalPrice + ", cash=" + cash + ", cashChange=" + cashChange + ", createdAt=" + createdAt + ", createdBy=" + createdBy + " ]";
    }

}
