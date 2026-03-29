package com.example.demo.service;

import com.example.demo.entity.DishEntity;
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
}
