package com.foodrec.backend.RecipeAPI.service;

import com.foodrec.backend.RecipeAPI.dto.NewRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;


public interface RecipeCommandService  {
    boolean insertRecipe(NewRecipeDTO newRec);
    boolean updateRecipeStatusById(String id);
    boolean updateRecipeDetailsById(RUDRecipeDTO rec);
}
