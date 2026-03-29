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
    public ResponseEntity<IngredientEntity> findByIngredient(@PathVariable Long id) {

        IngredientEntity ingredient = ingredientService.findByIdIngredient(id);

        if (ingredient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(ingredient);
    }
    @GetMapping("/{id}/stock")
    public ResponseEntity<?> getStockValueAt(
            @PathVariable Long id,
            @RequestParam String at,
            @RequestParam String unit) {

        StockValue stock = ingredientService.getStockValueAt(id, at, unit);

        if (stock == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stock);
    }
}