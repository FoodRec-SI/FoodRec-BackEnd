package com.foodrec.backend.RecipeAPI.command.update_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.ReadRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.UpdateRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.UpdateRecipeDTO;

public class UpdateRecipeCommand implements Command<ReadRecipeDTO> {
    private final UpdateRecipeDTO UpdateRecipeDTO;

    public UpdateRecipeCommand(UpdateRecipeDTO UpdateRecipeDTO) {
        this.UpdateRecipeDTO = UpdateRecipeDTO;
    }

    public UpdateRecipeDTO getUpdateRecipeDTO() {
        return UpdateRecipeDTO;
    }
}