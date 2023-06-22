package com.foodrec.backend.RecipeAPI.command.update_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.UpdateRecipeDTO;
import org.springframework.http.HttpStatus;

public class UpdateRecipeCommand implements Command<HttpStatus> {
    private final UpdateRecipeDTO UpdateRecipeDTO;
    private String userId;

    public UpdateRecipeCommand(UpdateRecipeDTO UpdateRecipeDTO, String userId) {
        this.UpdateRecipeDTO = UpdateRecipeDTO;
        this.userId = userId;
    }

    public UpdateRecipeDTO getUpdateRecipeDTO() {
        return UpdateRecipeDTO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}