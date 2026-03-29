package com.example.demo.entity;


import lombok.Data;

import java.util.List;

@Data
public class DishEntity {
    private Long id;
    private String name;
    private DishTypeEnum dishType;
    private List<IngredientEntity> ingredients;

    public double getDishPrice() {
        double total = 0.0;

        if (ingredients != null) {
            for (IngredientEntity ing : ingredients) {
                total += ing.getPrice();
            }
        }
        return total;
    }
}
