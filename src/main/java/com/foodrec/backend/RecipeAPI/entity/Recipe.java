package com.foodrec.backend.RecipeAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="recipe")
@Entity
public class Recipe {
    public Recipe() {
    }
    /*Quy tắc đặt tên cho thuộc tính: tên thuộc tính trong Spring
    phải trùng với tên cột trong bảng trong Database.,*/
    @Id
    @Column(name="recipeid")
    private String recipeId; //recipeId
    @Column(name="recipename")
    private String recipeName;
    @Column(name="description")
    private String description;
    @Column(name="calories")
    private int calories;
    @Column(name="username")
    private String username;
    @Column(name="duration")
    private int duration;
    @Column(name="image")
    private byte[] image;

    @Column(name="status")
    private boolean status;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
