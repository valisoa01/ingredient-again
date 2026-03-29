package com.example.demo.service;

import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.StockValue;
import com.example.demo.repository.IngredientRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

 private final IngredientRepository ingredientRepository;

 public IngredientService(IngredientRepository ingredientRepository) {
  this.ingredientRepository = ingredientRepository;
 }
 public List<IngredientEntity> findAllIngredient() {
  return ingredientRepository.findAllIngredient();
 }
 public IngredientEntity findByIdIngredient(Long id) {
  return ingredientRepository.findByIdIngredient(id);
 }
 public StockValue getStockValueAt(Long ingredientID, String at, String requestedUnit) {
  return ingredientRepository.getStockValueAt(ingredientID, at, requestedUnit);
 }
}