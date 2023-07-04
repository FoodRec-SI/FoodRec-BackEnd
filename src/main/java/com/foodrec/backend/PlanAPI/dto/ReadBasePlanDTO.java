package com.foodrec.backend.PlanAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadBasePlanDTO {
    private String planId;
    private String planName;
    private String planDescription;
    private String planDate;
}
