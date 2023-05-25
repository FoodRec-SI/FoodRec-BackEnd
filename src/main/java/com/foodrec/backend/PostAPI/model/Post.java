package com.foodrec.backend.PostAPI.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Time;

@Entity
@Table(name = "post")

public class Post {
    @Id
    @Column(name = "postid")
    private String postid;
    @Column(name = "time")
    private Time time;
    @Column(name = "status")
    private int status;
    @Column(name = "recipeid")
    private String recipeid;
    @Column(name = "userid")
    private String userid;
    @Column(name = "moderatorid")
    private String moderatorid;

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

