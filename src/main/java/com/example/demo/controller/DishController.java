package com.example.demo.controller;

import com.example.demo.entity.DishEntity;
import com.example.demo.entity.DishIngredientRequest;
import com.example.demo.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> updateDishIngredients(
            @PathVariable Long id,
            @RequestBody(required = false) List<DishIngredientRequest> requestBody) {

        // Vérification : Body obligatoire
        if (requestBody == null) {
            return ResponseEntity.badRequest()
                    .body("Request body is mandatory and must contain a list of ingredients");
        }

        String result = dishService.updateDishIngredients(id, requestBody);

        if ("DISH_NOT_FOUND".equals(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Dish.id=" + id + " is not found");
        }

        return ResponseEntity.ok().build(); // 200 OK - Succès
    }
}
