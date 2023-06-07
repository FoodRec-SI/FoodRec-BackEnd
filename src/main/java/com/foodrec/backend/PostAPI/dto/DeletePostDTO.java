package com.foodrec.backend.PostAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeletePostDTO {
    private String postId;
    @JsonProperty("username")
    private String userName;

    public DeletePostDTO() {
    }

    public DeletePostDTO(String postId, String userName) {
        this.postId = postId;
        this.userName = userName;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
