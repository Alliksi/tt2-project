package com.storage.restaurant.domain;

import com.storage.company.domain.Company;

import javax.persistence.*;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "registration_number", nullable = false, length = 11)
    private String registrationNumber;

    @Column(name = "address", nullable = false, length = 256)
    private String address;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}