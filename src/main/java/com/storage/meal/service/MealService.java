package com.storage.meal.service;

import com.storage.meal.domain.Meal;
import com.storage.meal.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService implements IMealService {
    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public List<Meal> getAll() {
        return mealRepository.findAll();
    }
}
