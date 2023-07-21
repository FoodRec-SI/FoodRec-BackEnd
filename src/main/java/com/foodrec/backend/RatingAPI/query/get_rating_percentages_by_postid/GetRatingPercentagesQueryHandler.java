package com.foodrec.backend.RatingAPI.query.get_rating_percentages_by_postid;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.RatingAPI.dto.RatingPercentageDTO;
import com.foodrec.backend.LikeAPI.entity.Likes;

import com.foodrec.backend.RatingAPI.entity.Rating;
import com.foodrec.backend.RatingAPI.repository.RatingRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetRatingPercentagesQueryHandler implements Command.Handler<GetRatingPercentagesQuery, RatingPercentageDTO> {
    private RatingRepository ratingRepository;

    public GetRatingPercentagesQueryHandler(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    private String convertToStringPercentage(int counts, int totalElements) {
        BigDecimal bd = new BigDecimal(Double.toString(counts*100/ totalElements));
        bd = bd.setScale(1, RoundingMode.UP);
        double avg = bd.doubleValue();
        String result = Double.toString(avg);
        return result;
    }

    @Override
    public RatingPercentageDTO handle(GetRatingPercentagesQuery command) {
        String postId = command.getPostId();
        List<Rating> ratingList = ratingRepository.getRatingsByPost_PostId(postId);
        Map<String, Integer> ratings = new HashMap<String, Integer>();



        ratings.put("fiveStars", 0);
        ratings.put("fourStars", 0);
        ratings.put("threeStars", 0);
        ratings.put("twoStars", 0);
        ratings.put("oneStar", 0);

        if(ratingList.isEmpty()){
            return new RatingPercentageDTO("0","0","0","0","0");
        };
        for (Rating eachRating : ratingList) {
            int score = eachRating.getScore();
            int count;
            switch (score) {
                case 5:
                    count = ratings.get("fiveStars");
                    count++;
                    ratings.put("fiveStars", count);
                    break;
                case 4:
                    count = ratings.get("fourStars");
                    count++;
                    ratings.put("fourStars", count);
                    break;
                case 3:
                    count = ratings.get("threeStars");
                    count++;
                    ratings.put("threeStars", count);
                    break;
                case 2:
                    count = ratings.get("twoStars");
                    count++;
                    ratings.put("twoStars", count);
                    break;
                case 1:
                    count = ratings.get("oneStar");
                    count++;
                    ratings.put("oneStar", count);
                    break;
            }
        }

        RatingPercentageDTO ratingPercentageDTO = new RatingPercentageDTO();
        ratingPercentageDTO.setFive_stars(convertToStringPercentage(ratings.get("fiveStars"), ratingList.size()));
        ratingPercentageDTO.setFour_stars(convertToStringPercentage(ratings.get("fourStars"), ratingList.size()));
        ratingPercentageDTO.setThree_stars(convertToStringPercentage(ratings.get("threeStars"), ratingList.size()));
        ratingPercentageDTO.setTwo_stars(convertToStringPercentage(ratings.get("twoStars"), ratingList.size()));
        ratingPercentageDTO.setOne_star(convertToStringPercentage(ratings.get("oneStar"), ratingList.size()));

        return ratingPercentageDTO;
    }
}
