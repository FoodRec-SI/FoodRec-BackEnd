package com.foodrec.backend.PostAPI.dto;

import com.foodrec.backend.PostAPI.entity.PostStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostDTO implements Serializable {
    private String postId;
    private String userId;
    private String moderatorId;
    private String recipeName;
    private String description;
    private int calories;
    private int duration;
    private String image;
    private LocalDateTime time;
    private PostStatus postStatus;
    private String recipeId;
    private LocalDateTime verifiedTime;

    public PostDTO() {
    }

    public PostDTO(String postId, String userId, String moderatorId, String recipeName,
                   String description, int calories, int duration, String image,
                   LocalDateTime time, PostStatus postStatus, String recipeId, LocalDateTime verifiedTime) {
        this.postId = postId;
        this.userId = userId;
        this.moderatorId = moderatorId;
        this.recipeName = recipeName;
        this.description = description;
        this.calories = calories;
        this.duration = duration;
        this.image = image;
        this.time = time;
        this.postStatus = postStatus;
        this.recipeId = recipeId;
        this.verifiedTime = verifiedTime;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(String moderatorId) {
        this.moderatorId = moderatorId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public LocalDateTime getVerifiedTime() {
        return verifiedTime;
    }

    public void setVerifiedTime(LocalDateTime verifiedTime) {
        this.verifiedTime = verifiedTime;
    }
}