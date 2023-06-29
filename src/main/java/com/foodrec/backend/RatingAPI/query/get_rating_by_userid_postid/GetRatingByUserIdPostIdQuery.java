package com.foodrec.backend.RatingAPI.query.get_rating_by_userid_postid;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RatingAPI.dto.RatingByUserIdPostIdDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRatingByUserIdPostIdQuery
        implements Command<RatingByUserIdPostIdDTO> {
    private String userId;
    private String postId;
}
