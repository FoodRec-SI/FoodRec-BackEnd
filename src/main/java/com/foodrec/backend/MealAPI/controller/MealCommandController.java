package com.foodrec.backend.MealAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.MealAPI.command.create_meal.CreateMealCommand;
import com.foodrec.backend.MealAPI.dto.CreateMealDTO;
import com.foodrec.backend.MealAPI.dto.MealDTO;
import com.foodrec.backend.Utils.GetCurrentUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "MealAPI")
@RestController
public class MealCommandController {
    final Pipeline pipeline;

    public MealCommandController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(description = "Create recommend meal for user. You must give meal name, max calories, tag id.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/meal", method = RequestMethod.POST)
    public ResponseEntity createPost(@RequestBody CreateMealDTO createMealDTO) {
        ResponseEntity responseEntity = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            CreateMealCommand command = new CreateMealCommand(createMealDTO, userId);
            MealDTO mealDTO = pipeline.send(command);
            responseEntity = new ResponseEntity<>(mealDTO, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error!");
        }
        return responseEntity;
    }
}
