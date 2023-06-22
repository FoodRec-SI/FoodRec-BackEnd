package com.foodrec.backend.PostAPI.entity;

import com.foodrec.backend.AccountAPI.entity.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Id
    @Column(name = "postid")
    private String postId;

    @Column(name = "status")
    private int status;

    @Column(name = "userid")
    private String userId;

    @Column(name = "moderatorid")
    private String moderatorId;

    @Column(name = "recipeid")
    private String recipeId;

    @Column(name = "recipename")
    private String recipeName;

    @Column(name = "description")
    private String description;

    @Column(name = "calories")
    private int calories;

    @Column(name = "duration")
    private int duration;

    @Column(name = "image")
    private String image;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "verified-time")
    private LocalDateTime verifiedTime;

    @OneToMany(mappedBy = "postid")
    private Set<PostCollection> postCollections;

    //M-M relationship with the Likes table (1 Account Likes Many Posts,
    //and 1 Post is Liked by Many Accounts)
    @ManyToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "postid"),
            inverseJoinColumns = @JoinColumn(name = "userid"))
    private Set<Account> accounts;
}

