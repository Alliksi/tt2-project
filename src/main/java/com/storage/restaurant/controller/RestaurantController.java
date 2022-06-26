package com.storage.restaurant.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestaurantController {

    @GetMapping(value={"/admin/restaurants"})
    public String showLoginView() {
        return "admins/restaurants/show_all_restaurants";
    }
    
}
