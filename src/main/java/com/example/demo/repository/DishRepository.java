package com.example.demo.repository;

import com.example.demo.entity.DishEntity;
import com.example.demo.entity.DishIngredientRequest;
import com.example.demo.entity.DishTypeEnum;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishRepository {

     public List<DishEntity> findAllDish() {
        List<DishEntity> dishes = new ArrayList<>();
        String sql = """
            SELECT id, name, dish_type 
            FROM dish
            """;

        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                DishEntity dish = new DishEntity();
                dish.setId(rs.getLong("id"));
                dish.setName(rs.getString("name"));
                dish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des plats", e);
        }
        return dishes;
    }

     public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM dish WHERE id = ?";

        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

     public void updateDishIngredients(Long dishId, List<DishIngredientRequest> requests) {

         String deleteSql = "DELETE FROM dish_ingredient WHERE dish_id = ?";

        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement pstmtDelete = conn.prepareStatement(deleteSql)) {

            pstmtDelete.setLong(1, dishId);
            pstmtDelete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression des ingrédients du plat", e);
        }

         String insertSql = """
            INSERT INTO dish_ingredient (dish_id, ingredient_id, quantity_required, unit)
            SELECT ?, id, 0.0, 'PCS'
            FROM ingredient 
            WHERE id = ?
            ON CONFLICT (dish_id, ingredient_id) DO NOTHING
            """;

        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement pstmtInsert = conn.prepareStatement(insertSql)) {

            for (DishIngredientRequest req : requests) {
                if (req.getId() != null) {
                    pstmtInsert.setLong(1, dishId);
                    pstmtInsert.setLong(2, req.getId());
                    pstmtInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'ajout des ingrédients au plat", e);
        }
    }
}