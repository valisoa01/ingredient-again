package com.example.demo.repository;


import com.example.demo.entity.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.sql.Timestamp;

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
    public List<StockMovement> getStockMovements(Long ingredientId, String from, String to) {

        String sql = """
            SELECT 
                id,
                creation_datetime,
                unit,
                quantity,
                movement_type
            FROM stock_movement
            WHERE ingredient_id = ?
              AND creation_datetime BETWEEN ?::timestamp AND ?::timestamp
            ORDER BY creation_datetime ASC
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            StockMovement stock = new StockMovement();

            stock.setId(rs.getLong("id"));

             Timestamp timestamp = rs.getTimestamp("creation_datetime");
            stock.setCreationDatetime(timestamp != null ? ((java.sql.Timestamp) timestamp).toInstant() : null);

            stock.setUnit(rs.getString("unit"));
            stock.setQuantity(rs.getDouble("quantity"));

             String movementTypeStr = rs.getString("movement_type");
            if (movementTypeStr != null) {
                stock.setMovementType(MovementType.valueOf(movementTypeStr));
            }

            return stock;
        }, ingredientId, from, to);
    }
}