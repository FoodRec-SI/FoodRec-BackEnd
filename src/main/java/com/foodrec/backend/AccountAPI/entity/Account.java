package com.foodrec.backend.AccountAPI.entity;

import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.TagAPI.entity.Tag;
import jakarta.persistence.*;

import java.util.ArrayList;
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
    @Column(name = "profile-image")
    private byte[] profileImage;
    @Column(name = "background-image")
    private byte[] backgroundImage;

    //M-M relationship with Tag table (relation). NOT A COLUMN.
    //Just imagine it as a WIRE that connects 2 tables.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_tag",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "tagid"))
    private List<Tag> tagAndAccountList = new ArrayList<>();

    //M-M relationship with the Likes table (1 Account Likes Many Posts,
    //and 1 Post is Liked by Many Accounts)

    @ManyToMany(mappedBy = "accounts")
    private Set<Post> posts;

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Account(){}

    public Account(String userId, String name, String description, byte[] profileImage, byte[] backgroundImage) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
        this.tagAndAccountList = tagAndAccountList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Tag> getTagAndAccountList() {
        return tagAndAccountList;
    }

    public void setTagAndAccountList(List<Tag> tagAndAccountList) {
        this.tagAndAccountList = tagAndAccountList;
    }
}
