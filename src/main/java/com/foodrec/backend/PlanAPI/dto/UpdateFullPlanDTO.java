package com.foodrec.backend.PlanAPI.dto;

import com.foodrec.backend.MealAPI.dto.MealPerPlanDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
//Entry point to fully update the Meal details of an existing Plan.
public class UpdateFullPlanDTO {
    private String planId;
    private Set<MealPerPlanDTO> mealSet;
}
