package com.example.demo.controller;

import com.example.demo.entity.IngredientEntity;
import com.example.demo.service.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IngredientController {

    private final IngredientService ingredientService;
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @GetMapping("/ingredients")
    public List<IngredientEntity> findAll() {
        return ingredientService.findAll();
    }
}