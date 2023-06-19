package com.foodrec.backend.RecipeAPI.entity;

import com.foodrec.backend.TagAPI.entity.Tag;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "recipe")
@Entity
public class Recipe {
    @Id
    @Column(name = "recipeid")
    private String recipeId;
    @Column(name = "recipename")
    private String recipeName;
    @Column(name = "description")
    private String description;
    @Column(name = "calories")
    private int calories;
    @Column(name = "userid")
    private String userId;
    @Column(name = "duration")
    private int duration;
    @Column(name = "image")
    private String image;
    @Column(name = "status")
    private boolean status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipe_tag",
            joinColumns = @JoinColumn(name = "recipeid"),
            inverseJoinColumns = @JoinColumn(name = "tagid")
    )
    private Set<Tag> tag = new HashSet<>();

    public Recipe() {
    }

    public Recipe(String recipeId, String recipeName, String description, int calories, String userId,
                  int duration, String image, boolean status, Set<Tag> tag) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.description = description;
        this.calories = calories;
        this.userId = userId;
        this.duration = duration;
        this.image = image;
        this.status = status;
        this.tag = tag;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<Tag> getTag() {
        return tag;
    }

    public void setTag(Set<Tag> tag) {
        this.tag = tag;
    }
}