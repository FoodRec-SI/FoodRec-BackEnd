package com.foodrec.backend.RecipeAPI.command.create_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import org.springframework.http.HttpStatus;

public class CreateRecipeCommand implements Command<HttpStatus> {
    private final CreateRecipeDTO createRecipeDTO;
    private final String userId;

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