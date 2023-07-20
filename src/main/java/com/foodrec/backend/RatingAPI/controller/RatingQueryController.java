package com.foodrec.backend.RatingAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.RatingAPI.dto.RatingByUserIdPostIdDTO;
import com.foodrec.backend.RatingAPI.dto.RatingDetailsDTO;
import com.foodrec.backend.RatingAPI.dto.RatingPercentageDTO;
import com.foodrec.backend.RatingAPI.query.get_rating_by_userid_postid.GetRatingByUserIdPostIdQuery;
import com.foodrec.backend.RatingAPI.query.get_rating_details_by_postid.GetRatingDetailsByPostIdQuery;
import com.foodrec.backend.RatingAPI.query.get_rating_percentages_by_postid.GetRatingPercentagesQuery;
import com.foodrec.backend.Utils.GetCurrentUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "RatingAPI")
@RestController

public class RatingQueryController {
    @Autowired
    final Pipeline pipeline;
    public RatingQueryController(Pipeline pipeline){
        this.pipeline = pipeline;
    }
    @Operation(
            description = "Returns the number of voters, and the average score of a Post.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/rating/{postId}", method = RequestMethod.GET)
    public ResponseEntity getRatingDetailsByPostId(@PathVariable String postId){
        ResponseEntity result = null;
        try{
            GetRatingDetailsByPostIdQuery getRatingDetailsByPostIdQuery
                    = new GetRatingDetailsByPostIdQuery(postId);
            RatingDetailsDTO ratingDetailsDTO = pipeline.send(getRatingDetailsByPostIdQuery);
            result = new ResponseEntity(ratingDetailsDTO, HttpStatus.OK);
        }catch(Exception e){

        }
        return result;
    }
    @Operation(
            description = "Returns the rating percentages of a post.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/rating/percentage/{postId}", method = RequestMethod.GET)
    public ResponseEntity getRatingPercentages(@PathVariable String postId){
        ResponseEntity result = null;
        try {
            GetRatingPercentagesQuery getRatingPercentagesQuery =
                    new GetRatingPercentagesQuery(postId);
            RatingPercentageDTO ratingPercentageDTO = pipeline.send(getRatingPercentagesQuery);
            if(ratingPercentageDTO!=null){
                result = new ResponseEntity(ratingPercentageDTO, HttpStatus.OK);
            }
        }catch(Exception e){
            result = new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    @Operation(
            description = "Returns the rating based on the UserId and PostId.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/rating/user/post/{postId}", method = RequestMethod.GET)
    public ResponseEntity getRatingByUserIdPostId(@PathVariable String postId){
        ResponseEntity result = null;
        Authentication authentication = null;
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            GetRatingByUserIdPostIdQuery getRatingByUserIdPostIdQuery =
                    new GetRatingByUserIdPostIdQuery(userId,postId);
            RatingByUserIdPostIdDTO ratingByUserIdPostIdDTO =
                    pipeline.send(getRatingByUserIdPostIdQuery);
            if(ratingByUserIdPostIdDTO!=null){
                result = new ResponseEntity(ratingByUserIdPostIdDTO, HttpStatus.OK);
            }
        }catch(Exception e){
            result = new ResponseEntity("Something went wrong on the server side, and we are still investigating..."
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
