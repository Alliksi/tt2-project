package com.storage.meal.controller;

import com.storage.meal.service.IMealService;
import com.storage.product.domain.Product;
import com.storage.product.service.IProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MealController {

    private final IMealService _mealService;

    public MealController(IMealService mealService) {
        this._mealService = mealService;
    }

}