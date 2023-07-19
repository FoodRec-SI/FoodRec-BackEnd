package com.foodrec.backend.RatingAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingPercentageDTO {
    private String five_stars;
    private String four_stars;
    private String three_stars;
    private String two_stars;
    private String one_star;
}
