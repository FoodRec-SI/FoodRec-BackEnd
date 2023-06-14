package com.foodrec.backend.PostAPI.dto;

import com.foodrec.backend.PostAPI.entity.PostStatus;

public class UpdatePostDTO {
    private String postId;
    private PostStatus status;
    private String moderatorId;

    public UpdatePostDTO() {
    }

    public UpdatePostDTO(String postId, PostStatus status, String moderatorId) {
        this.postId = postId;
        this.status = status;
        this.moderatorId = moderatorId;
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

    public String getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(String moderatorId) {
        this.moderatorId = moderatorId;
    }
}