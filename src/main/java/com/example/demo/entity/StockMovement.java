package com.example.demo.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class StockMovement {
    private Long id;
    private Instant creationDatetime;
    private String unit;
    private double quantity;
    private MovementType movementType;
}
