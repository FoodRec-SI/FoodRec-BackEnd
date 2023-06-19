package com.foodrec.backend.TagAPI.query.get_tags_by_recipeid;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.TagAPI.dto.TagDTO;

import java.util.List;

public class GetTagsByRecipeIdQuery implements Command<List<TagDTO>> {
    private String recipeId;

    public GetTagsByRecipeIdQuery() {
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }
}
