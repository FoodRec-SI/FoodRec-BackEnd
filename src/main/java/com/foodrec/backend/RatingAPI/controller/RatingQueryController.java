package com.foodrec.backend.RatingAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.RatingAPI.dto.RatingDetailsDTO;
import com.foodrec.backend.RatingAPI.query.GetRatingDetailsByPostIdCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            GetRatingDetailsByPostIdCommand getRatingDetailsByPostIdCommand
                    = new GetRatingDetailsByPostIdCommand(postId);
            RatingDetailsDTO ratingDetailsDTO = pipeline.send(getRatingDetailsByPostIdCommand);
            result = new ResponseEntity(ratingDetailsDTO, HttpStatus.OK);
        }catch(Exception e){

        }
        return result;
    }

}
