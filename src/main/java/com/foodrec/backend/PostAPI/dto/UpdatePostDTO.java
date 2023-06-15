package com.foodrec.backend.PostAPI.dto;

import com.foodrec.backend.PostAPI.entity.PostStatus;

public class UpdatePostDTO {
    private String postId;
    private PostStatus status;

    public UpdatePostDTO() {
    }

    public UpdatePostDTO(String postId, PostStatus status) {
        this.postId = postId;
        this.status = status;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }
}