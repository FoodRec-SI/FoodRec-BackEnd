package com.foodrec.backend.RecipeAPI.utils;

import com.foodrec.backend.RecipeAPI.dto.NewRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;

import java.util.ArrayList;

public interface Utils {
    boolean containsAnyEmptyField(RecipeDTO rec, ArrayList<String> exceptionList)
            throws IllegalAccessException;
    String generateRecId();

}
