package com.example.demo.controller;

import com.example.demo.entity.DishEntity;
import com.example.demo.service.DishService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }
    @GetMapping
    public List<DishEntity> findAllDish() {
        return dishService.findAllDish();
    }
}
