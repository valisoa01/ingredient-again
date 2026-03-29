package com.example.demo.service;

import com.example.demo.entity.IngredientEntity;
import com.example.demo.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

 private final IngredientRepository ingredientRepository;

 public IngredientService(IngredientRepository ingredientRepository) {
  this.ingredientRepository = ingredientRepository;
 }
 public List<IngredientEntity> findAll() {
  return ingredientRepository.findAll();
 }
}