package com.foodrec.backend.MealAPI.entity.dto;

import com.foodrec.backend.PostAPI.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*This DTO is used to contain a list of Posts
 which are entered from the front-end,
 AFTER the user has generated a couple of meals.
* */
public class MealPerPlanDTO {
    private String mealName;
    private Set<PostDTO> postSet; //1 Meal contains a Set of Posts
}
