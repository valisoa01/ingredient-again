package com.example.demo.repository;


import com.example.demo.entity.CategoryEnum;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.StockValue;
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
    public StockValue getStockValueAt(Long ingredientId, String at, String requestedUnit) {

        String sql = """
        SELECT COALESCE(
                    SUM(CASE WHEN movement_type = 'IN'  THEN quantity ELSE 0 END) -
                    SUM(CASE WHEN movement_type = 'OUT' THEN quantity ELSE 0 END),
                    0
               ) as stock_quantity
        FROM stock_movement 
        WHERE ingredient_id = ? 
          AND creation_datetime <= ?::timestamp
        """;

        try {
            Double quantity = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{ingredientId, at},
                    Double.class
            );

            if (quantity == null) {
                quantity = 0.0;
            }

            return new StockValue(quantity, requestedUnit);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}