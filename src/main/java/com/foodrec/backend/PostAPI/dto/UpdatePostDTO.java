package com.foodrec.backend.PostAPI.dto;

public class UpdatePostDTO {
    private String postid;
    private int status;
    private String moderatorid;

    public UpdatePostDTO(String postid, int status, String moderatorid) {
        this.postid = postid;
        this.status = status;
        this.moderatorid = moderatorid;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getModeratorid() {
        return moderatorid;
    }

    public void setModeratorid(String moderatorid) {
        this.moderatorid = moderatorid;
    }
}
