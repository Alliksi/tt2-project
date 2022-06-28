package com.storage.user.domain;

import com.storage.restaurant.domain.Restaurant;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;
    @NotBlank(message = "Username cannot be empty!")
    @Size(min = 4, max = 64, message = "Username must be at least 4 characters long!")
    @Column(name = "username", nullable = false, length = 128)
    private String username;

    @NotBlank(message = "Password cannot be empty!")
    @Size(min = 4, max = 64, message = "Password must be at least 4 characters long!")
    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @NotBlank(message = "Email cannot be empty!")
    @Column(name = "email", nullable = false, length = 128)
    private String email;

    @Column(name = "enabled")
    private Boolean enabled;

    @NotBlank(message = "Name cannot be empty!")
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "roles", nullable = false, length = 256)
    private String roles;

    @NotBlank(message = "Surname cannot be empty!")
    @Column(name = "surname", nullable = false, length = 128)
    private String surname;

    @NotBlank(message = "Personal code cannot be empty!")
    @Size(min = 11, max = 11, message = "Personal code must be at 11 characters long!")
    @Column(name = "personal_code", nullable = false, length = 11)
    private String personalCode;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "picture", length = 256)
    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}