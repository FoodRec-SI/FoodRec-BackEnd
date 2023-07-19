package com.foodrec.backend.PlanAPI.dto;

import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.MealAPI.dto.MealDTO;
import com.foodrec.backend.MealAPI.entity.Meal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReadFullPlanDTO {
    private String planId;
    private String planName;
    private String userId;
    private String description;
    private String ingredientList;
    private LocalDateTime date;
    private int calories;
    private Set<MealDTO> mealSet;
}
