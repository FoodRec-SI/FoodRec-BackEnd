package com.foodrec.backend.RecipeAPI.entity;

import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.TagAPI.entity.Tag;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "recipe")
@Entity
public class Recipe {
    @Id
    @Column(name = "recipeid")
    private String recipeId; //recipeId
    @Column(name = "recipename")
    private String recipeName;
    @Column(name = "description")
    private String description;
    @Column(name = "calories")
    private int calories;
    @Column(name = "username")
    private String userName;
    @Column(name = "duration")
    private int duration;
    @Column(name = "image")
    private byte[] image;
    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "recipe")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "recipe_tag",
            joinColumns = @JoinColumn(name = "recipeid"),
            inverseJoinColumns = @JoinColumn(name = "tagid")
    )
    private Set<Tag> tagSet = new HashSet<>();

    public Set<Tag> getTagSet() {
        return tagSet;
    }

    public void setTagSet(Set<Tag> tagSet) {
        this.tagSet = tagSet;
    }

    public Recipe() {
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

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}