package com.foodrec.backend.RatingAPI.query.get_rating_by_userid_postid;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RatingAPI.dto.RatingByUserIdPostIdDTO;
import com.foodrec.backend.RatingAPI.entity.Rating;
import com.foodrec.backend.RatingAPI.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetRatingByUserIdPostIdQueryHandler implements
        Command.Handler<GetRatingByUserIdPostIdQuery, RatingByUserIdPostIdDTO> {
    private RatingRepository ratingRepository;

    public GetRatingByUserIdPostIdQueryHandler(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }
    @Override
    public RatingByUserIdPostIdDTO handle(GetRatingByUserIdPostIdQuery query) {
        Rating ratingByUserIdPostId = ratingRepository.
                findRatingByAccount_UserIdAndPost_PostId(query.getUserId(), query.getPostId());
        if(ratingByUserIdPostId == null) return null;

        RatingByUserIdPostIdDTO rating = new RatingByUserIdPostIdDTO();
        rating.setRating(ratingByUserIdPostId.getScore());

        return rating;
    }
}
