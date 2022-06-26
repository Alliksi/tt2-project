package com.storage.meal.repository;

import com.storage.meal.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MealRepository extends JpaRepository<Meal, Integer> {
}