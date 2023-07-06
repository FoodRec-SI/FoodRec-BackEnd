package com.foodrec.backend.PlanAPI.dto;

import com.foodrec.backend.MealAPI.dto.CreateMealPerPlanDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//Entry point to fully update the Meal details of an existing Plan.
public class UpdateFullPlanDTO {
    private String planId;
    private List<CreateMealPerPlanDTO> mealPerPlanList;
}
