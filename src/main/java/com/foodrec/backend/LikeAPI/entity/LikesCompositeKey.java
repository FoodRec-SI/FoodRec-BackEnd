package com.foodrec.backend.LikeAPI.entity;

import jakarta.persistence.*;

import java.io.Serializable;


public class LikesCompositeKey implements Serializable{
    @Column(name="userid")
    private String userId;
    @Column(name="postid")
    private String postId;
    public LikesCompositeKey(String userId, String postId){
        this.userId = userId;
        this.postId = postId;
    }
    public LikesCompositeKey(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }


}
