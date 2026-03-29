package com.example.demo.repository;


import com.example.demo.entity.CategoryEnum;
import com.example.demo.entity.IngredientEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
 public class IngredientRepository {
    private final JdbcTemplate jdbcTemplate;

    public IngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<IngredientEntity> findAllIngredient() {
        String sql = """
        SELECT 
            id, name, price, category FROM ingredient 
     
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            IngredientEntity ing = new IngredientEntity();
            ing.setId(rs.getLong("id"));
            ing.setName(rs.getString("name"));
            ing.setPrice(rs.getDouble("price"));
            ing.setCategory(CategoryEnum.valueOf(rs.getString("category")));
            return ing;
        });
    }
    public IngredientEntity findByIdIngredient(Long id) {
        String sql = "SELECT id, name, price, category from ingredient where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                IngredientEntity ing = new IngredientEntity();
                ing.setId(rs.getLong("id"));
                ing.setName(rs.getString("name"));
                ing.setPrice(rs.getDouble("price"));
                ing.setCategory(
                        CategoryEnum.valueOf(rs.getString("category")));
                return ing;
            });
        }
        catch (Exception e)
        {
            return null;
        }
    }
}