package com.foodrec.backend.RatingAPI.query.get_rating_percentages_by_postid;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RatingAPI.dto.RatingPercentageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRatingPercentagesQuery implements Command<RatingPercentageDTO> {
    private String postId;
}
