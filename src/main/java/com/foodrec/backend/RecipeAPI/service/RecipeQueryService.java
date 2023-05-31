package com.foodrec.backend.RecipeAPI.service;

import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;

import java.util.List;

public interface RecipeQueryService {
    List<RUDRecipeDTO> findAllRecipes();
    RUDRecipeDTO findRecipeByRecipeid(String recipeid);
}
