package com.storage.meals_products.domain;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "meals_products", schema = "public", catalog = "restaurant-storage")
@IdClass(MealsProductsPK.class)
public class MealsProducts {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
    private int productId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "meal_id")
    private int mealId;
    @Basic
    @Column(name = "amount")
    private BigInteger amount;

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

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealsProducts that = (MealsProducts) o;
        return productId == that.productId && mealId == that.mealId && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, mealId, amount);
    }
}
