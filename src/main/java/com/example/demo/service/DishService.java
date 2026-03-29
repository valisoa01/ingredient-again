package com.example.demo.service;

import com.example.demo.entity.DishEntity;
import com.example.demo.entity.DishIngredientRequest;
import com.example.demo.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    private DishRepository dishRepository;
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }
    public List<DishEntity> findAllDish() {
        return dishRepository.findAllDish();
    }

    public String updateDishIngredients(Long id, List<DishIngredientRequest> requestBody) {
        if (!dishRepository.existsById(id)) {
            return "DISH_NOT_FOUND";
        }
        dishRepository.updateDishIngredients(id, requestBody);
        return "SUCCESS";
    }
}
