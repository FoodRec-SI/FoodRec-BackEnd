package com.foodrec.backend.PostAPI.dto;

import com.foodrec.backend.PostAPI.entity.PostStatus;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO implements Serializable {
    private String postId;
    private String userId;
    private String moderatorId;
    private String recipeId;
    private String recipeName;
    private String description;
    private int calories;
    private int duration;
    private String image;
    private LocalDateTime createdTime;
    private PostStatus postStatus;
    private LocalDateTime verifiedTime;
    private double averageScore;
    private String ingredientList;
    private String instruction;
}