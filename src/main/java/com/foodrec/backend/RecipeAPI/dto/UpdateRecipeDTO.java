package com.foodrec.backend.RecipeAPI.dto;

import com.foodrec.backend.TagAPI.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecipeDTO implements Serializable {
    private String recipeId;
    private String recipeName;
    private String description;
    private int calories;
    private int duration;
    private MultipartFile image;
    private String instructions;
    private List<String> tagsIdList;

}
