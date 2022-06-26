package com.storage.restaurant.controller;


import com.storage.product.domain.Product;
import com.storage.restaurant.domain.Restaurant;
import com.storage.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RestaurantController {

    final RestaurantService _restaurantService;

    public RestaurantController(RestaurantService _restaurantService) {
        this._restaurantService = _restaurantService;
    }

    @GetMapping(value={"/owner/restaurants"})
    public String showRestaurantDashboard(Model model) {
        List<Restaurant> restaurantList = _restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurantList);
        return "owners/restaurants/dashboard";
    }
    
}
