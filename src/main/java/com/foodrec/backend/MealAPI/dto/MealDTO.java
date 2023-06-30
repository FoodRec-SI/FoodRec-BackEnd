package com.foodrec.backend.MealAPI.dto;

import com.foodrec.backend.PostAPI.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDTO {
    private String mealId;
    private String mealName;
    private int currentCalories;
    private List<PostDTO> postDTOList;
    private String message;
}
