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
    private String recipeid;
    @Column(name="recipename")
    private String recipename;
    @Column(name="description")
    private String description;
    @Column(name="calories")
    private int calories;
    @Column(name="userid")
    private String userid;
    @Column(name="duration")
    private int duration;
    @Column(name="image")
    private byte[] image;

    @Column(name="hidden")
    private boolean hidden;


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }



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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
