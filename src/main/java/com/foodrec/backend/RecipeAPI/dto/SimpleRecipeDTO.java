package com.foodrec.backend.RecipeAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleRecipeDTO {
    private String recipeId;
    private String message;
}
