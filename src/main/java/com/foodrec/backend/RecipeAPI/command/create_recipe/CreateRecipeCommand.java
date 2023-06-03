package com.foodrec.backend.RecipeAPI.command.create_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.NewRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;

public class CreateRecipeCommand implements Command<Boolean> {
    private final NewRecipeDTO newRecipeDTO;

    public CreateRecipeCommand(NewRecipeDTO newRecipeDTO) {
        this.newRecipeDTO = newRecipeDTO;
    }

    public NewRecipeDTO getRudRecipeDTO() {
        return newRecipeDTO;
    }
}
