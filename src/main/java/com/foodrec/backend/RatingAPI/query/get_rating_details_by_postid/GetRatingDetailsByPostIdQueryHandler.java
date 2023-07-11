package com.foodrec.backend.RatingAPI.query.get_rating_details_by_postid;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RatingAPI.dto.RatingDetailsDTO;
import com.foodrec.backend.RatingAPI.entity.Rating;
import com.foodrec.backend.RatingAPI.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class GetRatingDetailsByPostIdQueryHandler
        implements Command.Handler<GetRatingDetailsByPostIdQuery, RatingDetailsDTO> {
    @Autowired
    private RatingRepository ratingRepository;

    public GetRatingDetailsByPostIdQueryHandler(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }
    @Override
    public RatingDetailsDTO handle(GetRatingDetailsByPostIdQuery command) {
        String postId = command.getPostId();
        List<Rating> ratingListByPostId = ratingRepository.getRatingsByPost_PostId(postId);

        int raters = ratingListByPostId.size();
        double sum = 0;
        for(Rating eachRating:ratingListByPostId){
            int eachScore = eachRating.getScore();
            sum+=eachScore;
        }

        BigDecimal bd = new BigDecimal(Double.toString(sum/raters));
        bd = bd.setScale(1, RoundingMode.DOWN);
        double avg = bd.doubleValue();

        RatingDetailsDTO ratingDetailsDTO = new RatingDetailsDTO();
        ratingDetailsDTO.setAverage(avg);
        ratingDetailsDTO.setRaters(raters);

        return ratingDetailsDTO;
    }
}
