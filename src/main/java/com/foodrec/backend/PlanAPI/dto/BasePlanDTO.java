package com.foodrec.backend.PlanAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//returned to the front-end when a new plan is created.
public class BasePlanDTO {
    private String planId;
    private String planName;
    private String planDescription;
    private String planDate;
}
