package com.foodrec.backend.AccountAPI.entity;

import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.TagAPI.entity.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "userid")
    private String userId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "profile-image")
    private String profileImageName;
    @Column(name = "background-image")
    private String backgroundImageName;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_tag",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "tagid"))
    private Set<Tag> accountTags = new HashSet<>();

    //M-M relationship with the Likes table (1 Account Likes Many Posts,
    //and 1 Post is Liked by Many Accounts)

    @ManyToMany(mappedBy = "accounts")
    private Set<Post> posts;
}
