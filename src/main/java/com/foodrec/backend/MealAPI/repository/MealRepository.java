package com.foodrec.backend.MealAPI.repository;

import com.foodrec.backend.MealAPI.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, String> {
}
