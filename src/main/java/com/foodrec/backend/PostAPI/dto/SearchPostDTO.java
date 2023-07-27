package com.foodrec.backend.PostAPI.dto;

import com.foodrec.backend.TagAPI.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPostDTO {
    private String postId;
    private String recipeId;
    private String recipeName;
    private String description;
    private String image;
    private int duration;
    private double averageScore;
    private List<TagDTO> tagDTOList;
}
