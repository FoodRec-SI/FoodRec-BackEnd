package com.foodrec.backend.PostAPI.dto;

public class CreatePostDTO {
    private String recipeId;

    public CreatePostDTO() {
    }

    public CreatePostDTO(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }
}
