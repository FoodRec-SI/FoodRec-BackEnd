package com.foodrec.backend.MealAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMealDTO {
    private String mealName;
    private int maxCalories;
    private Set<String> tagIds;
}
