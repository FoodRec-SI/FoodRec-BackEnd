package com.foodrec.backend.RatingAPI.query.get_rating_details_by_postid;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RatingAPI.dto.RatingDetailsDTO;

public class GetRatingDetailsByPostIdQuery implements Command<RatingDetailsDTO> {
    private String postId;
    public GetRatingDetailsByPostIdQuery(String postId){
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }
}
