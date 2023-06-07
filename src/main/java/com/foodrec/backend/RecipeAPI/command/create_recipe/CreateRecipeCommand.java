package com.foodrec.backend.RecipeAPI.command.create_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.ReadRecipeDTO;

public class CreateRecipeCommand implements Command<ReadRecipeDTO> {
    private final CreateRecipeDTO createRecipeDTO;

    public CreateRecipeCommand(CreateRecipeDTO createRecipeDTO) {
        this.createRecipeDTO = createRecipeDTO;
    }

    public CreateRecipeDTO getCreateRecipeDTO() {
        return createRecipeDTO;
    }
}
