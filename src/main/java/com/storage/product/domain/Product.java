package com.storage.product.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "products", schema = "public", catalog = "restaurant-storage")
@NamedQuery(name = "Products.GetAll", query = "SELECT e FROM Product e")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
    private int productId;
    @Basic
    @Column(name = "amount_left")
    private BigDecimal amountLeft;
    @Basic
    @Column(name = "date_of_expiry")
    private Date dateOfExpiry;
    @Basic
    @Column(name = "storage_type")
    private String storageType;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "shelf_id")
    private Integer shelfId;
    @Basic
    @Column(name = "delivery_id")
    private Integer deliveryId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public BigDecimal getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(BigDecimal amountLeft) {
        this.amountLeft = amountLeft;
    }

    public Date getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(Date dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShelfId() {
        return shelfId;
    }

    public void setShelfId(Integer shelfId) {
        this.shelfId = shelfId;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return productId == that.productId && Objects.equals(amountLeft, that.amountLeft) && Objects.equals(dateOfExpiry, that.dateOfExpiry) && Objects.equals(storageType, that.storageType) && Objects.equals(name, that.name) && Objects.equals(shelfId, that.shelfId) && Objects.equals(deliveryId, that.deliveryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, amountLeft, dateOfExpiry, storageType, name, shelfId, deliveryId);
    }
}
