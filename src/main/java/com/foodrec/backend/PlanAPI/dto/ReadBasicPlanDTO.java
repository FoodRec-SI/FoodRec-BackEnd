package com.foodrec.backend.PlanAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//This DTO is used to return the full Plan Data to the Front-end,
//after the creation process is completed.
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReadBasicPlanDTO {
    private String planId;
    private String planName;
    private String description;
    /*Sidenote: This ingredient-list
     * is derived from ALL Meals within a Plan.*/
    private String ingredientList;
    private int mealQuantity;
    private LocalDateTime date;
    private int calories;
    private String image;
}
