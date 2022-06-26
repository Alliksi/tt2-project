package com.storage.restaurant.service;

import com.storage.restaurant.domain.Restaurant;

import java.util.List;

public interface IRestaurantService {
    List<Restaurant> getAllRestaurants();

    Restaurant addRestaurant(Restaurant restaurant);

    List<Restaurant> getAllRestaurantsByCompanyId(Integer companyId);

    Restaurant getRestaurantById(Integer restaurantId);

    Boolean deleteRestaurant(Restaurant restaurant);

    Boolean deleteRestaurant(Integer restaurantId);

    Restaurant updateRestaurant(Restaurant restaurant, Integer restaurantIdToUpdate);
}
