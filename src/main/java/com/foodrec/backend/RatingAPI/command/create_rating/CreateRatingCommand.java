package com.foodrec.backend.RatingAPI.command.create_rating;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RatingAPI.dto.CreateRatingDTO;
import com.foodrec.backend.RatingAPI.dto.RatingDTO;

public class CreateRatingCommand implements Command<RatingDTO> {
    private CreateRatingDTO createRatingDTO;
    private String userId;

    public CreateRatingCommand(CreateRatingDTO createRatingDTO, String userId){
        this.createRatingDTO = createRatingDTO;
        this.userId = userId;
    }

    public CreateRatingDTO getCreateRatingDTO() {
        return createRatingDTO;
    }

    public String getUserId() {
        return userId;
    }
}

