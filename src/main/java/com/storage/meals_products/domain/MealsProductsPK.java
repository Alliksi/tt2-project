package com.storage.meals_products.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class MealsProductsPK implements Serializable {
    @Column(name = "product_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    @Column(name = "meal_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mealId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealsProductsPK that = (MealsProductsPK) o;
        return productId == that.productId && mealId == that.mealId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, mealId);
    }
}
