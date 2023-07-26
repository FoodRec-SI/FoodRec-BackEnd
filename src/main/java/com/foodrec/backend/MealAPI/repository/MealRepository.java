package com.foodrec.backend.MealAPI.repository;

import com.foodrec.backend.MealAPI.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, String> {
    Optional<Meal> getFirstByPlanIdOrderByMealNameAsc (String planId);
}
