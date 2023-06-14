package com.foodrec.backend.PostAPI.dto;

public class CreatePostDTO {
    private String recipeId;
    private String userId;

    public CreatePostDTO() {
    }

    public CreatePostDTO(String recipeId, String userId) {
        this.recipeId = recipeId;
        this.userId = userId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
