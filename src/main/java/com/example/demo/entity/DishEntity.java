package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dish")
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private DishTypeEnum dishType;
    @OneToMany
    @JoinColumn(name = "ingredient_id")
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
