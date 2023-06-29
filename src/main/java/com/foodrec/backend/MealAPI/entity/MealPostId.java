package com.foodrec.backend.MealAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPostId implements Serializable {
    @Column(name = "mealid")
    private String mealId;
    @Column(name = "postId")
    private String postId;
}
