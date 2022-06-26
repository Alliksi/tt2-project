package com.storage.delivery_company.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "delivery_companies", schema = "public", catalog = "restaurant-storage")
public class DeliveryCompany {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "delivery_company_id")
    private int deliveryCompanyId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "registration_number")
    private String registrationNumber;

    public int getDeliveryCompanyId() {
        return deliveryCompanyId;
    }

    public void setDeliveryCompanyId(int deliveryCompanyId) {
        this.deliveryCompanyId = deliveryCompanyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryCompany that = (DeliveryCompany) o;
        return deliveryCompanyId == that.deliveryCompanyId && Objects.equals(name, that.name) && Objects.equals(registrationNumber, that.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryCompanyId, name, registrationNumber);
    }
}
