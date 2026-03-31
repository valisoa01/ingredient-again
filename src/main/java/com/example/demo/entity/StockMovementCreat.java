package com.example.demo.entity;

import lombok.Data;

@Data
public class StockMovementCreat {
    private String unit;
    private double quantity;
    private MovementType movementType;
}
