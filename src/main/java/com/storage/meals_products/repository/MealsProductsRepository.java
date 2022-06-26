package com.storage.meals_products.repository;

import com.storage.meals_products.domain.MealsProducts;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MealsProductsRepository extends JpaRepository<MealsProducts, Integer> {
}