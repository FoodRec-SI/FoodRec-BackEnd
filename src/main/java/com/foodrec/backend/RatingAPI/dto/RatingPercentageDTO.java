package com.foodrec.backend.RatingAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingPercentageDTO {
    private String _5_stars;
    private String _4_stars;
    private String _3_stars;
    private String _2_stars;
    private String _1_star;
}
