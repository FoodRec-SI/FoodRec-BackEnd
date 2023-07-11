package com.foodrec.backend.PostAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.MealAPI.entity.MealPost;
import com.foodrec.backend.LikeAPI.entity.Likes;
import com.foodrec.backend.RatingAPI.entity.Rating;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter /*Use Lombok's getter and setter to prevent Stack Overflow error.*/
@Setter
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

    @Column(name = "created-time")
    private LocalDateTime createdTime;

    @Column(name = "verified-time")
    private LocalDateTime verifiedTime;
    @Column(name = "average-score")
    private double averageScore;

    @Column(name = "ingredient-list")
    private String ingredientList;

    @Column(name = "instruction")
    private String instruction;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private Set<PostCollection> postCollections;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<MealPost> mealPosts;

    @OneToMany(mappedBy = "post") /*mappedBy: Bind this entity (Post)
                                        to the entity with the same name
                                        in the Join table (Likes/Ratings)                                        */
    private Set<Likes> likes; /*The name of the join table*/

    @OneToMany(mappedBy = "post")
    private Set<Rating> ratings;
}

