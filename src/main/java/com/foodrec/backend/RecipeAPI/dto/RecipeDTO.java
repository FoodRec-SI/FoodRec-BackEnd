package com.foodrec.backend.RecipeAPI.dto;

import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private String recipeId;
    private String recipeName;
    private String description;
    private int calories;
    private int duration;
    private String image;
    private String instructions;
    private List<TagDTO> tags;
}
