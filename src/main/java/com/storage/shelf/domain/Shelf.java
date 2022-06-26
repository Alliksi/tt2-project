package com.storage.shelf.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Shelf {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "shelf_id")
    private int shelfId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "storage_type")
    private String storageType;
    @Basic
    @Column(name = "allocated_space")
    private BigDecimal allocatedSpace;
    @Basic
    @Column(name = "storage_id")
    private Integer storageId;

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public BigDecimal getAllocatedSpace() {
        return allocatedSpace;
    }

    public void setAllocatedSpace(BigDecimal allocatedSpace) {
        this.allocatedSpace = allocatedSpace;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return shelfId == shelf.shelfId && Objects.equals(name, shelf.name) && Objects.equals(storageType, shelf.storageType) && Objects.equals(allocatedSpace, shelf.allocatedSpace) && Objects.equals(storageId, shelf.storageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelfId, name, storageType, allocatedSpace, storageId);
    }
}
