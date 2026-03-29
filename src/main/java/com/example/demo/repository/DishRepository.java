package com.example.demo.repository;

import com.example.demo.entity.DishEntity;
import com.example.demo.entity.DishIngredientRequest;
import com.example.demo.entity.DishTypeEnum;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DishRepository {
    private final JdbcTemplate jdbcTemplate;
    public DishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<DishEntity> findAllDish() {
        String sql = """
                select 
                    id, name, dish_type from dish
                """;
        return jdbcTemplate.query(sql, (rs,rowNum) ->{
            DishEntity dish = new DishEntity();
            dish.setId(rs.getLong("id"));
            dish.setName(rs.getString("name"));
            dish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
            return dish;
        });
    }
    // Vérifier l'existence du plat
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM dish WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    // Mise à jour des ingrédients du plat
    public void updateDishIngredients(Long dishId, List<DishIngredientRequest> requests) {

        // 1. Supprimer toutes les anciennes associations
        jdbcTemplate.update("DELETE FROM dish_ingredient WHERE dish_id = ?", dishId);

        // 2. Ajouter les nouvelles associations (uniquement si l'ingrédient existe en BDD)
        String insertSql = """
        INSERT INTO dish_ingredient (dish_id, ingredient_id, quantity_required, unit)
        SELECT ?, id, 0.0, 'PCS'
        FROM ingredient 
        WHERE id = ?
        ON CONFLICT (dish_id, ingredient_id) DO NOTHING
        """;

        for (DishIngredientRequest req : requests) {
            if (req.getId() != null) {
                jdbcTemplate.update(insertSql, dishId, req.getId());
            }
        }
    }
}
