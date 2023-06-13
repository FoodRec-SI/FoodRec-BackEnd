package com.foodrec.backend.AccountAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class Account {
    @Id
    @Column(name = "userid")
    private String userId;
    @Column(name = "description")
    private String description;
    @Column(name = "profile-image")
    private byte[] profileImage;
    @Column(name = "background-image")
    private byte[] backgroundImage;

    public Account() {
    }

    public Account(String userId, String description, byte[] profileImage, byte[] backgroundImage) {
        this.userId = userId;
        this.description = description;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public byte[] getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(byte[] backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
