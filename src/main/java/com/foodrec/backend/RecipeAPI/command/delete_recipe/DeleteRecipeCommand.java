package com.foodrec.backend.RecipeAPI.command.delete_recipe;

import an.awesome.pipelinr.Command;

public class DeleteRecipeCommand implements Command<Boolean> {
    private final String recipeid;
    public DeleteRecipeCommand(String recipeid){
        this.recipeid = recipeid;
    }

    public String getRecipeid(){
        return recipeid;
    }

}
