package com.foodrec.backend.AccountAPI.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.foodrec.backend.LikeAPI.entity.Likes;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.RatingAPI.entity.Rating;
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
    @JsonBackReference
    private Set<Tag> accountTags = new HashSet<>();

    @OneToMany(mappedBy = "account") /*mappedBy: This Entity (Account) is mapped to
                                        the Account entity in the Join Table (Likes)*/
    private Set<Likes> likes;

    @OneToMany(mappedBy = "account")/*mappedBy: This Entity (Account) is mapped to                                        the Account entity in the Join Table (Rating)*/
    private Set<Rating> ratings;
}
