package com.foodrec.backend.MealAPI.entity;

import com.foodrec.backend.PostAPI.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meal_post")
public class MealPost {

    @EmbeddedId
    private MealPostId mealPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "postid", insertable = false, updatable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("mealId")
    @JoinColumn(name = "mealid", insertable = false, updatable = false)
    private Meal meal;
}
