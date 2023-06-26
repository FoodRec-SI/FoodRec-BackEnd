package com.foodrec.backend.RecipeAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecipeDTO implements Serializable {
    private String recipeId;
    private String recipeName;
    private String description;
    private int calories;
    private int duration;
    private MultipartFile imageFile;
    private String instructions;
    private String ingredientList;
    private Set<String> tagIdSet;
}
