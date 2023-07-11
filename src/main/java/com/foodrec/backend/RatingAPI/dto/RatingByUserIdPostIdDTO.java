package com.foodrec.backend.RatingAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingByUserIdPostIdDTO {
    private int rating;
}
