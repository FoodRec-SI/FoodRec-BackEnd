package com.foodrec.backend.PostAPI.dto;


public class CreatePostDTO{
    private String recipeid;
    private String userid;

    public CreatePostDTO(String recipeid, String userid) {
        this.recipeid = recipeid;
        this.userid = userid;
    }

    public String getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(String recipeid) {
        this.recipeid = recipeid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
