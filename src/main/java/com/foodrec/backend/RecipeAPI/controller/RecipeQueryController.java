package com.foodrec.backend.RecipeAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.query.get_recipe_by_id.GetRecipeByIdQuery;
import com.foodrec.backend.RecipeAPI.query.get_recipe_by_user_id.GetRecipeByUserIdQuery;
import com.foodrec.backend.Utils.GetCurrentUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "RecipeAPI")
@RestController
public class RecipeQueryController {

    final Pipeline pipeline;

    public RecipeQueryController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }


    @Operation(description = "Gets all Recipes of 1 USER",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})

    @RequestMapping(value = "/api/member/recipe", method = RequestMethod.GET)
    public ResponseEntity getRecipesByUserId(@RequestParam(defaultValue = "0") String pageNumber,
                                             @RequestParam(defaultValue = "6") String pageSize) {
        ResponseEntity responseEntity;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userid = GetCurrentUserData.getCurrentUserId(authentication);
            GetRecipeByUserIdQuery query = new GetRecipeByUserIdQuery(userid, pageNumber, pageSize);
            Page<RecipeDTO> result = pipeline.send(query);
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error!");
        }
        return responseEntity;
    }

    @Operation(description = "Get recipe by recipe ID. Only user who create this recipe can view",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/member/recipe/{recipeId}")
    public ResponseEntity getRecipeById(@PathVariable String recipeId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userid = GetCurrentUserData.getCurrentUserId(authentication);
            GetRecipeByIdQuery query = new GetRecipeByIdQuery(recipeId, userid);
            RecipeDTO result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | UnauthorizedExceptionHandler | NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
