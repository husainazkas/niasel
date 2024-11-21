/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.entity;

import java.util.Objects;

/**
 *
 * @author husainazkas
 */
public class SalesOrderItem {

    private Long id;
    private SalesOrder order;
    private Product product;
    private Integer count;
    private long price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SalesOrder getOrder() {
        return order;
    }

    public void setOrder(SalesOrder order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += Objects.hashCode(id);
        hash += Objects.hashCode(order);
        hash += Objects.hashCode(product);
        hash += Objects.hashCode(count);
        hash += Objects.hashCode(price);
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
        final SalesOrderItem other = (SalesOrderItem) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.order, other.order)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.count, other.count)) {
            return false;
        }
        return Objects.equals(this.price, other.price);
    }

    @Override
    public String toString() {
        return "pos.model.SalesOrderItem[ id=" + id + ", order=" + order + ", product=" + product + ", count=" + count + ", price=" + price + " ]";
    }

}
