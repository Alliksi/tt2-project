package com.storage.meal.controller;

import com.storage.meal.service.IMealService;
import org.springframework.stereotype.Controller;

@Controller
public class MealController {

    private final IMealService _mealService;

    public MealController(IMealService mealService) {
        this._mealService = mealService;
    }

}