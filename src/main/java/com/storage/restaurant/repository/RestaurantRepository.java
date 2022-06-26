package com.storage.restaurant.repository;

import com.storage.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findAllByCompanyId(Integer companyId);
}