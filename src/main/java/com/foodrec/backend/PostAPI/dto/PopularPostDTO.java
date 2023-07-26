package com.foodrec.backend.PostAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopularPostDTO {
    private String postId;
    private String recipeName;
    private String image;
    private int duration;
    private String description;
    private double averageScore;
}
