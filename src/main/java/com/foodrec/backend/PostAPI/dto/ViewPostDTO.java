package com.foodrec.backend.PostAPI.dto;

import java.time.LocalDateTime;

public class ViewPostDTO {
    private String postid;
    private LocalDateTime time;
    private String recipeid;
    private String userid;
    private String moderatorid;

    public ViewPostDTO() {
    }

    public ViewPostDTO(String postid, LocalDateTime time, String recipeid, String userid, String moderatorid) {
        this.postid = postid;
        this.time = time;
        this.recipeid = recipeid;
        this.userid = userid;
        this.moderatorid = moderatorid;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
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

    public String getModeratorid() {
        return moderatorid;
    }

    public void setModeratorid(String moderatorid) {
        this.moderatorid = moderatorid;
    }
}