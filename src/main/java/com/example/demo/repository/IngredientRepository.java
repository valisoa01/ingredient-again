package com.example.demo.repository;

import com.example.demo.entity.CategoryEnum;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.MovementType;
import com.example.demo.entity.StockMovement;
import com.example.demo.entity.StockValue;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientRepository {


    public List<IngredientEntity> findAllIngredient() {
        List<IngredientEntity> ingredients = new ArrayList<>();
        String sql = "SELECT id, name, price, category FROM ingredient";

        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                IngredientEntity ing = new IngredientEntity();
                ing.setId(rs.getLong("id"));
                ing.setName(rs.getString("name"));
                ing.setPrice(rs.getDouble("price"));
                ing.setCategory(CategoryEnum.valueOf(rs.getString("category")));
                ingredients.add(ing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des ingrédients", e);
        }
        return ingredients;
    }

     public IngredientEntity findByIdIngredient(Long id) {
        String sql = "SELECT id, name, price, category FROM ingredient WHERE id = ?";

        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    IngredientEntity ing = new IngredientEntity();
                    ing.setId(rs.getLong("id"));
                    ing.setName(rs.getString("name"));
                    ing.setPrice(rs.getDouble("price"));
                    ing.setCategory(CategoryEnum.valueOf(rs.getString("category")));
                    return ing;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

     public StockValue getStockValueAt(Long ingredientId, String at, String requestedUnit) {
        String sql = """
            SELECT COALESCE(
                SUM(CASE WHEN movement_type = 'IN' THEN quantity ELSE 0 END) -
                SUM(CASE WHEN movement_type = 'OUT' THEN quantity ELSE 0 END),
                0
            ) as stock_quantity
            FROM stock_movement 
            WHERE ingredient_id = ? 
              AND creation_datetime <= ?::timestamp
            """;

        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, ingredientId);
            pstmt.setString(2, at);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double quantity = rs.getDouble("stock_quantity");
                    return new StockValue(quantity, requestedUnit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new StockValue(0.0, requestedUnit);
    }


    public List<StockMovement> getStockMovements(Long ingredientId, String from, String to) {
        List<StockMovement> movements = new ArrayList<>();
        String sql = """
            SELECT id, creation_datetime, unit, quantity, movement_type
            FROM stock_movement
            WHERE ingredient_id = ?
              AND creation_datetime BETWEEN ?::timestamp AND ?::timestamp
            ORDER BY creation_datetime ASC
            """;

        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, ingredientId);
            pstmt.setString(2, from);
            pstmt.setString(3, to);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    StockMovement stock = new StockMovement();
                    stock.setId(rs.getLong("id"));
                    Timestamp ts = rs.getTimestamp("creation_datetime");
                    stock.setCreationDatetime(ts != null ? ts.toInstant() : null);
                    stock.setUnit(rs.getString("unit"));
                    stock.setQuantity(rs.getDouble("quantity"));

                    String typeStr = rs.getString("movement_type");
                    if (typeStr != null) {
                        stock.setMovementType(MovementType.valueOf(typeStr));
                    }
                    movements.add(stock);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movements;
    }

     public List<StockMovement> createStockMovements(Long ingredientId, List<StockMovement> dtos) {

        List<StockMovement> createdList = new ArrayList<>();

        String insertSql = """
            INSERT INTO stock_movement 
                (ingredient_id, quantity, unit, movement_type, creation_datetime)
            VALUES (?, ?, ?, ?, NOW())
            RETURNING id, creation_datetime, unit, quantity, movement_type
            """;

        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

            for (StockMovement dto : dtos) {
                pstmt.setLong(1, ingredientId);
                pstmt.setDouble(2, dto.getQuantity());
                pstmt.setString(3, dto.getUnit());
                pstmt.setString(4, dto.getMovementType().name());   // .name() car c'est un Enum

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        StockMovement sm = new StockMovement();
                        sm.setId(rs.getLong("id"));
                        sm.setCreationDatetime(rs.getTimestamp("creation_datetime").toInstant());
                        sm.setUnit(rs.getString("unit"));
                        sm.setQuantity(rs.getDouble("quantity"));
                        sm.setMovementType(MovementType.valueOf(rs.getString("movement_type")));
                        createdList.add(sm);
                    }
                }
            }
            return createdList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création des mouvements de stock pour l'ingrédient " + ingredientId, e);
        }
    }
}