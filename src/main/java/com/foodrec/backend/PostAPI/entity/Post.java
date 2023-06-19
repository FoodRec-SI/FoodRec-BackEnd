package com.foodrec.backend.PostAPI.entity;

import com.foodrec.backend.CollectionAPI.entity.Collection;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.AccountAPI.entity.Account;
import java.util.HashSet;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "post")

public class Post implements Serializable {
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
    private String image;
    @Column(name = "time")
    private LocalDateTime time;
    @Column(name = "verified-time")
    private LocalDateTime verifiedTime;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "post_collection",
            joinColumns = {@JoinColumn(name = "postid")},
            inverseJoinColumns = {@JoinColumn(name = "collectionid")})
    private Set<Collection> collections = new HashSet<>();


    //M-M relationship with the Likes table (1 Account Likes Many Posts,
    //and 1 Post is Liked by Many Accounts)
    @ManyToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "postid"),
            inverseJoinColumns = @JoinColumn(name = "userid"))
    private Set<Account> accounts;

    public Post() {
    }

    public Post(String postId, int status, String userId, String moderatorId,
                String recipeId, String recipeName, String description, int calories,
                int duration, String image, LocalDateTime time, LocalDateTime verifiedTime, Set<Collection> collections) {
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
        this.verifiedTime = verifiedTime;
        this.collections = collections;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getVerifiedTime() {
        return verifiedTime;
    }

    public void setVerifiedTime(LocalDateTime verifiedTime) {
        this.verifiedTime = verifiedTime;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Set<Collection> getCollections() {
        return collections;
    }

    public void setCollections(Set<Collection> collections) {
        this.collections = collections;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}

