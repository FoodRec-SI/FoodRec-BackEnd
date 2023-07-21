package com.foodrec.backend.RecipeAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.DuplicateExceptionHandler;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.RecipeAPI.command.create_recipe.CreateRecipeCommand;
import com.foodrec.backend.RecipeAPI.command.delete_recipe.DeleteRecipeCommand;
import com.foodrec.backend.RecipeAPI.command.update_recipe.UpdateRecipeCommand;
import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.SimpleRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.UpdateRecipeDTO;
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

@Tag(name = "RecipeAPI")
@RestController
public class RecipeCommandController {
    final Pipeline pipeline;

    public RecipeCommandController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(description = "Create a Recipe of 1 User",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/recipe", method = RequestMethod.POST
            , consumes = "multipart/form-data")
    public ResponseEntity createRecipe(@ModelAttribute CreateRecipeDTO newRec) {
        ResponseEntity responseEntity;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            CreateRecipeCommand createRecipeCommand = new CreateRecipeCommand(newRec, userId);
            String recipeId = pipeline.send(createRecipeCommand);
            responseEntity = new ResponseEntity<>(recipeId, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error!");
        }
        return responseEntity;
    }

    @Operation(description = "Updates the Recipe Details of 1 USER",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/recipe", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public ResponseEntity updateRecipeById(@ModelAttribute UpdateRecipeDTO updateRecipeDTO) {
        ResponseEntity responseEntity;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            UpdateRecipeCommand updateRecipeCommand = new UpdateRecipeCommand(updateRecipeDTO, userId);
            SimpleRecipeDTO result = pipeline.send(updateRecipeCommand);
            responseEntity = new ResponseEntity<>(result,HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler | DuplicateExceptionHandler |
                 UnauthorizedExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error!");
        }
        return responseEntity;
    }

    @Operation(description = "Deletes the Recipe Details of 1 USER",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/recipe/{recipeId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRecipeById(@PathVariable String recipeId) {
        ResponseEntity responseEntity;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            DeleteRecipeCommand command = new DeleteRecipeCommand(recipeId, userId);
            String result = pipeline.send(command);
            responseEntity = new ResponseEntity<>(result,HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | DuplicateExceptionHandler | UnauthorizedExceptionHandler |
                 NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error!");
        }
        return responseEntity;
    }
}
