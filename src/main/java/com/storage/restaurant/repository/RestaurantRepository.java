package com.storage.restaurant.repository;

import com.storage.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}