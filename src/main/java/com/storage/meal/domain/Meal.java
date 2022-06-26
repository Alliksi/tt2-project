package com.storage.meal.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "meals", schema = "public", catalog = "restaurant-storage")
public class Meal {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "meal_id")
    private int mealId;
    @Basic
    @Column(name = "amount")
    private BigDecimal amount;
    @Basic
    @Column(name = "restaurant_id")
    private Integer restaurantId;
    @Basic
    @Column(name = "menu_content_id")
    private Integer menuContentId;

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getMenuContentId() {
        return menuContentId;
    }

    public void setMenuContentId(Integer menuContentId) {
        this.menuContentId = menuContentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return mealId == meal.mealId && Objects.equals(amount, meal.amount) && Objects.equals(restaurantId, meal.restaurantId) && Objects.equals(menuContentId, meal.menuContentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, amount, restaurantId, menuContentId);
    }
}
