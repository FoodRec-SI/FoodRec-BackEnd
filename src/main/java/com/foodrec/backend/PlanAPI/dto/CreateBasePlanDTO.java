package com.foodrec.backend.PlanAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateBasePlanDTO {
    private String planName;
    private String planDescription;
    private Date planDate;
}
