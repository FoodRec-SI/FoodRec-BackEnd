package com.foodrec.backend.RecipeAPI.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class RUDRecipeDTO implements RecipeDTO{ //DTO cho việc lấy công thức (có thêm Id so với NewRecipeDTO)
    public RUDRecipeDTO() {
    }
    @Id
    private String recipeid;
    @Column(name="recipename")
    private String recipename;
    @Column(name="description")
    private String description;

    public String getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(String recipeid) {
        this.recipeid = recipeid;
    }

    public String getRecipename() {
        return recipename;
    }

    public void setRecipename(String recipename) {
        this.recipename = recipename;
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

    @Column(name="calories")
    private int calories;
    @Column(name="duration")
    private int duration;
    @Column(name="image")
    private byte[] image;

}
