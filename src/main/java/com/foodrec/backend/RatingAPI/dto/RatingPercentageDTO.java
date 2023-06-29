package com.foodrec.backend.RatingAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingPercentageDTO {
    private String fiveStars;
    private String fourStars;
    private String threeStars;
    private String twoStars;
    private String oneStar;
}
