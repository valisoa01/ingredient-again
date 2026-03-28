package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredient")
public class IngredientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private CategoryEnum category;
    @ManyToOne
    @JoinColumn(name = "dish_id")
    private DishEntity dish;

    public String getDishName() {
        if (dish != null) {
            return  dish.getName();
        }
        return null;
    }

}
