package com.example.demo.entity;

import lombok.Data;


@Data
public class IngredientEntity {
    private Long id;
    private String name;
    private double price;
     private CategoryEnum category;
    private DishEntity dish;

    public String getDishName() {
        if (dish != null) {
            return  dish.getName();
        }
        return null;
    }

}
