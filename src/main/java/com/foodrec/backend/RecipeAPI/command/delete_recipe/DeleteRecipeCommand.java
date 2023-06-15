package com.foodrec.backend.RecipeAPI.command.delete_recipe;

import an.awesome.pipelinr.Command;

public class DeleteRecipeCommand implements Command<Boolean> {
    private final String recipeId;
    private final String userId;
    public DeleteRecipeCommand(String recipeId,String userId) {
        this.recipeId = recipeId;
        this.userId = userId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getUserId() {
        return userId;
    }
}
