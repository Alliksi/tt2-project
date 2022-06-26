package com.storage.restaurant.service;


import com.storage.restaurant.domain.Restaurant;
import com.storage.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService implements IRestaurantService{

    @Autowired
    RestaurantRepository _restaurantRepository;

    public List<Restaurant> getAllRestaurants(){
        return _restaurantRepository.findAll();
    }
}
