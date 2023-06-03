package com.foodrec.backend.RecipeAPI.command.update_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;

public class UpdateRecipeCommand implements Command<Boolean> {
    private final RUDRecipeDTO rudRecipeDTO;

    public UpdateRecipeCommand(RUDRecipeDTO rudRecipeDTO) {
        this.rudRecipeDTO = rudRecipeDTO;
    }

    public RUDRecipeDTO getRUDRecipeDTO() {
        return rudRecipeDTO;
    }
}