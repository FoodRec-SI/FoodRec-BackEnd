package com.foodrec.backend.PostAPI.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePostDTO {
    private String recipeId;
    @JsonProperty("username")
    private String userName;

    public CreatePostDTO() {
    }

    public CreatePostDTO(String recipeId, String userName) {
        this.recipeId = recipeId;
        this.userName = userName;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
