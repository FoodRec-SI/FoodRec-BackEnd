package com.foodrec.backend.PostAPI.dto;

import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPerMealDTO {
    private String postId;
    private String recipeName;
    private String description;
    private int calories;
    private int duration;
    private String image;

    private double averageScore;
    private String ingredientList;
    private String instruction;
}
