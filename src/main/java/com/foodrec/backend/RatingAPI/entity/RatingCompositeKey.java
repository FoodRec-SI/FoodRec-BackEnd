package com.foodrec.backend.RatingAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RatingCompositeKey implements Serializable {
    @Column(name="userid")
    private String userId;
    @Column(name="postid")
    private String postId;

    public RatingCompositeKey(){}
    public RatingCompositeKey(String userId, String postId) {
        this.userId = userId;
        this.postId = postId;
    }

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
