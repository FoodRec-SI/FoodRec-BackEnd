package com.foodrec.backend.PlanAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasePlanDTO {
    private String planName;
    private String planDescription;
}
