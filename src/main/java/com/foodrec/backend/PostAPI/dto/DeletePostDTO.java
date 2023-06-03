package com.foodrec.backend.PostAPI.dto;

public class DeletePostDTO {
    private String postid;
    private String userid;

    public DeletePostDTO(String postid, String userid) {
        this.postid = postid;
        this.userid = userid;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
