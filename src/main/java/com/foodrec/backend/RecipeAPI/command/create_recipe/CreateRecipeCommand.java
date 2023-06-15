package com.foodrec.backend.RecipeAPI.command.create_recipe;

import an.awesome.pipelinr.Command;

import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;

public class CreateRecipeCommand implements Command<RecipeDTO> {
    private final CreateRecipeDTO createRecipeDTO;
    private String userId;

    public CreateRecipeCommand(CreateRecipeDTO createRecipeDTO, String userId) {
        this.createRecipeDTO = createRecipeDTO;
        this.userId = userId;
    }

    public CreateRecipeDTO getCreateRecipeDTO() {
        return createRecipeDTO;
    }

    public String getUserid() {
        return userId;
    }
}