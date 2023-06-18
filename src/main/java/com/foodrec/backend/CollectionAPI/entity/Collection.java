package com.foodrec.backend.CollectionAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodrec.backend.PostAPI.entity.Post;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "collection")
public class Collection {
    @Id
    @Column(name = "collectionid")
    private String collectionId;
    @Column(name = "collectionname")
    private String collectionName;
    @Column(name = "description")
    private String description;
    @Column(name = "userid")
    private String userId;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            },
            mappedBy = "collections")
    @JsonIgnore
    private Set<Post> posts = new HashSet<>();

    public Collection() {
    }

    public Collection(String collectionId, String collectionName, String description, String userId, Set<Post> posts) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.description = description;
        this.userId = userId;
        this.posts = posts;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
