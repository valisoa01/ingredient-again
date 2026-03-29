package com.example.demo.controller;

import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.StockValue;
import com.example.demo.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<IngredientEntity> findAllIngredient() {
        return ingredientService.findAllIngredient();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIngredient(@PathVariable Long id) {

        IngredientEntity ingredient = ingredientService.findByIdIngredient(id);

        if (ingredient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("L'ingrédient avec l'ID " + id + " n'existe pas.");

        }
        return ResponseEntity.ok(ingredient);
    }
    @GetMapping("/{id}/stock")
    public ResponseEntity<?> getStockValueAt(
            @PathVariable Long id,
            @RequestParam(required = false) String at,
            @RequestParam(required = false) String unit) {
        if (at == null || at.isBlank() || unit == null || unit.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Either mandatory query parameter `at` or `unit` is not provided.");
        }
        IngredientEntity ingredient = ingredientService.findByIdIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ingredient.id = " +id + " not found.");
        }
        StockValue stock = ingredientService.getStockValueAt(id, at, unit);
        return ResponseEntity.ok(stock);
    }
}