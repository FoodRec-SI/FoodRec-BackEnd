package com.foodrec.backend.MealAPI.dto;

import com.foodrec.backend.PostAPI.dto.CreatePostPerMealDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
//This DTO is used when creating a list of Meals for a Plan.
public class CreateMealPerPlanDTO {
    private String mealId;
    private String mealName;
    private int currentCalories;
    private List<CreatePostPerMealDTO> postList;
}
