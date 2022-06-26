package com.storage.restaurant.service;


import com.storage.restaurant.domain.Restaurant;
import com.storage.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService implements IRestaurantService{

    final RestaurantRepository _restaurantRepository;

    public RestaurantService(RestaurantRepository _restaurantRepository) {
        this._restaurantRepository = _restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants(){
        return _restaurantRepository.findAll();
    }

    public List<Restaurant> getAllRestaurantsByCompanyId(Integer companyId){
        return _restaurantRepository.findAllByCompanyId(companyId);
    }

    public Restaurant addRestaurant(Restaurant restaurant){
        return _restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurantById(Integer restaurantId) {
        return _restaurantRepository.getById(restaurantId);
    }

    public Boolean deleteRestaurant(Restaurant restaurant){
        _restaurantRepository.delete(restaurant);
        return true;
    }

    public Boolean deleteRestaurant(Integer restaurantId){
        _restaurantRepository.deleteById(restaurantId);
        return true;
    }

    public Restaurant updateRestaurant(Restaurant restaurant, Integer restaurantIdToUpdate){
        Restaurant restaurantToUpdate = _restaurantRepository.findById(restaurantIdToUpdate).orElse(null);
        restaurantToUpdate.setName(restaurant.getName());
        restaurantToUpdate.setAddress(restaurant.getAddress());
        restaurantToUpdate.setRegistrationNumber(restaurant.getRegistrationNumber());

        return _restaurantRepository.save(restaurantToUpdate);
    }
}
