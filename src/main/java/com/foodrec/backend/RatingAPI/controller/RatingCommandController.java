package com.foodrec.backend.RatingAPI.controller;


import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.RatingAPI.command.RatingCommand;
import com.foodrec.backend.RatingAPI.dto.CreateRatingDTO;
import com.foodrec.backend.RatingAPI.dto.RatingDTO;
import com.foodrec.backend.Utils.GetCurrentUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "RecipeAPI")
@RestController

public class RatingCommandController {
    final Pipeline pipeline;

    public RatingCommandController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/rating", method = RequestMethod.POST)
    public ResponseEntity createRating(@RequestBody CreateRatingDTO createRatingDTO){
        ResponseEntity result = null;
        Authentication authentication = null;
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserId.getCurrentUserId(authentication);
            RatingCommand ratingCommand = new RatingCommand(createRatingDTO,userId);
            RatingDTO ratingDTO = pipeline.send(ratingCommand);
            if (ratingDTO == null) {
                result = new ResponseEntity<String>("Couldn't add rating.",
                        HttpStatus.BAD_REQUEST);

            } else {
                result = new ResponseEntity<RatingDTO>(ratingDTO, HttpStatus.OK);
            }
        } catch (InvalidDataExceptionHandler e) {
            result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return result;
    }
}
