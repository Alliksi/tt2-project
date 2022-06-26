package com.storage.storage.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "storages", schema = "public", catalog = "restaurant-storage")
public class Storage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "storage_id")
    private int storageId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "size")
    private BigDecimal size;
    @Basic
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
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
        Storage storage = (Storage) o;
        return storageId == storage.storageId && Objects.equals(name, storage.name) && Objects.equals(size, storage.size) && Objects.equals(restaurantId, storage.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storageId, name, size, restaurantId);
    }
}
