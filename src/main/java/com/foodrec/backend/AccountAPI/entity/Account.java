package com.foodrec.backend.AccountAPI.entity;

import com.foodrec.backend.TagAPI.entity.Tag;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table
@Entity
public class Account {
    @Id
    @Column(name = "userid")
    private String userId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "profile-image-name")
    private String profileImageName;
    @Column(name = "background-image-name")
    private String backgroundImageName;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_tag",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "tagid"))
    private List<Tag> tagAndAccountList = new ArrayList<>();

    public Account() {
    }

    public Account(String userId, String name, String description, String profileImageName, String backgroundImageName) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.profileImageName = profileImageName;
        this.backgroundImageName = backgroundImageName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileImageName() {
        return profileImageName;
    }

    public void setProfileImageName(String profileImageName) {
        this.profileImageName = profileImageName;
    }

    public String getBackgroundImageName() {
        return backgroundImageName;
    }

    public void setBackgroundImageName(String backgroundImageName) {
        this.backgroundImageName = backgroundImageName;
    }

    public List<Tag> getTagAndAccountList() {
        return tagAndAccountList;
    }

    public void setTagAndAccountList(List<Tag> tagAndAccountList) {
        this.tagAndAccountList = tagAndAccountList;
    }
}
