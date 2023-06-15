package com.foodrec.backend.RatingAPI.query;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RatingAPI.dto.RatingDetailsDTO;

public class GetRatingDetailsByPostIdCommand implements Command<RatingDetailsDTO> {
    private String postId;
    public GetRatingDetailsByPostIdCommand(String postId){
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }
}
