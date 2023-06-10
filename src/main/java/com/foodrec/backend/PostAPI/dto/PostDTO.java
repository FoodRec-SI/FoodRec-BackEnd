package com.foodrec.backend.PostAPI.dto;

import com.foodrec.backend.PostAPI.entity.PostStatus;

import java.time.LocalDateTime;

public class PostDTO {
    private String postId;
    private String userName;
    private String moderatorName;
    private String recipeName;
    private String description;
    private int calories;
    private int duration;
    private byte[] image;
    private LocalDateTime time;
    private PostStatus postStatus;
    private String recipeId;

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public PostDTO() {
    }

    public PostDTO(String postId, String userName, String moderatorName,
                   String recipeName, String description, int calories,
                   int duration, byte[] image, LocalDateTime time, PostStatus postStatus) {
        this.postId = postId;
        this.userName = userName;
        this.moderatorName = moderatorName;
        this.recipeName = recipeName;
        this.description = description;
        this.calories = calories;
        this.duration = duration;
        this.image = image;
        this.time = time;
        this.postStatus = postStatus;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getModeratorName() {
        return moderatorName;
    }

    public void setModeratorName(String moderatorName) {
        this.moderatorName = moderatorName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }
}