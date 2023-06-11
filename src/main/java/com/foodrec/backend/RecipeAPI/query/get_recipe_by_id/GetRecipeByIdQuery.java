package com.foodrec.backend.RecipeAPI.query.get_recipe_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;

public class GetRecipeByIdQuery implements Command<RecipeDTO> {
    private final String recipeid;

    public GetRecipeByIdQuery(String recipeid) {
        this.recipeid = recipeid;
    }

    public String getRecipeid() {
        return recipeid;
    }
}

