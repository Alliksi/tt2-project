package com.storage.storage.repository;

import com.storage.restaurant.domain.Restaurant;
import com.storage.storage.domain.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StorageRepository extends JpaRepository<Storage, Integer> {
    List<Storage> findAllByRestaurantId(Integer restaurantId);
}