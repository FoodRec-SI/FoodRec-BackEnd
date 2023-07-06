package com.foodrec.backend.PostAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*This DTO is for CREATING the full Post Details
 * of a Meal, before inserting that Meal into the Plan.
 * */
public class CreatePostPerMealDTO {
    private String postId;
    private String ingredientList;
    private int calories;
}
