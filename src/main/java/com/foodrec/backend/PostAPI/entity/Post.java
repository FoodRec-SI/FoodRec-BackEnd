package com.foodrec.backend.PostAPI.entity;

import com.foodrec.backend.RecipeAPI.entity.Recipe;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")

public class Post {
    @Id
    @Column(name = "postid")
    private String postId;

    @Column(name = "status")
    private int status;
    @Column(name = "userid")
    private String userId;
    @Column(name = "moderatorid")
    private String moderatorId;
    @Column(name = "recipeid")
    private String recipeId;
    @Column(name = "recipename")
    private String recipeName;
    @Column(name = "description")
    private String description;
    @Column(name = "calories")
    private int calories;
    @Column(name = "duration")
    private int duration;
    @Column(name = "image")
    private byte[] image;
    @Column(name = "time")
    private LocalDateTime time;

    public Post() {
    }

    public Post(String postId, int status, String userId, String moderatorId, String recipeId, String recipeName,
                String description, int calories, int duration, byte[] image, LocalDateTime time) {
        this.postId = postId;
        this.status = status;
        this.userId = userId;
        this.moderatorId = moderatorId;
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.description = description;
        this.calories = calories;
        this.duration = duration;
        this.image = image;
        this.time = time;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
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
}

