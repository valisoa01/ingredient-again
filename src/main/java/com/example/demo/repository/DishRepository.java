package com.example.demo.repository;

import com.example.demo.entity.DishEntity;
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
}
