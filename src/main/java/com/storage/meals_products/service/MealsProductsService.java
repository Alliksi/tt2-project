package com.storage.meals_products.service;

import com.storage.meals_products.domain.MealsProducts;
import com.storage.meals_products.repository.MealsProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealsProductsService implements IMealsProductsService {
    private final MealsProductsRepository mealsProductsRepository;

    public MealsProductsService(MealsProductsRepository mealsProductsRepository) {
        this.mealsProductsRepository = mealsProductsRepository;
    }

    @Override
    public List<MealsProducts> getAll() {
        return mealsProductsRepository.findAll();
    }
}
