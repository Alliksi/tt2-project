package com.storage.delivery.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "deliveries", schema = "public", catalog = "restaurant-storage")
public class Delivery {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "delivery_id")
    private int deliveryId;
    @Basic
    @Column(name = "price")
    private Integer price;
    @Basic
    @Column(name = "delivery_time")
    private Date deliveryTime;
    @Basic
    @Column(name = "delivery_company_id")
    private Integer deliveryCompanyId;
    @Basic
    @Column(name = "administrator_id")
    private Integer administratorId;
    @Basic
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getDeliveryCompanyId() {
        return deliveryCompanyId;
    }

    public void setDeliveryCompanyId(Integer deliveryCompanyId) {
        this.deliveryCompanyId = deliveryCompanyId;
    }

    public Integer getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Integer administratorId) {
        this.administratorId = administratorId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery that = (Delivery) o;
        return deliveryId == that.deliveryId && Objects.equals(price, that.price) && Objects.equals(deliveryTime, that.deliveryTime) && Objects.equals(deliveryCompanyId, that.deliveryCompanyId) && Objects.equals(administratorId, that.administratorId) && Objects.equals(restaurantId, that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId, price, deliveryTime, deliveryCompanyId, administratorId, restaurantId);
    }
}
