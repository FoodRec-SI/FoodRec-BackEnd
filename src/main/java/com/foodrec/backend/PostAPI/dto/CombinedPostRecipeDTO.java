package com.foodrec.backend.PostAPI.dto;

import java.time.LocalDateTime;

public class CombinedPostRecipeDTO {
    private String postid;
    private LocalDateTime time;
    private String userId;
    private String moderatorId;
    private String recipeid;
    private String recipeName;
    private String description;
    private int calories;
    private int duration;
    private byte[] image;

    public CombinedPostRecipeDTO() {
    }

    public CombinedPostRecipeDTO(String postid, LocalDateTime time, String userId,
                                 String moderatorId, String recipeid, String recipeName,
                                 String description, int calories, int duration,
                                 byte[] image) {
        this.postid = postid;
        this.time = time;
        this.userId = userId;
        this.moderatorId = moderatorId;
        this.recipeid = recipeid;
        this.recipeName = recipeName;
        this.description = description;
        this.calories = calories;
        this.duration = duration;
        this.image = image;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
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

    public String getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(String recipeid) {
        this.recipeid = recipeid;
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
}