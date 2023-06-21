package com.foodrec.backend.RecipeAPI.controller;


import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.RecipeAPI.command.create_recipe.CreateRecipeCommand;
import com.foodrec.backend.RecipeAPI.command.delete_recipe.DeleteRecipeCommand;
import com.foodrec.backend.RecipeAPI.command.update_recipe.UpdateRecipeCommand;
import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.UpdateRecipeDTO;
import com.foodrec.backend.Exception.*;
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

    /*@RequestBody: v.d. khi bên Front-end gửi yêu cầu tạo 1 Recipe, đây là những gì nó sẽ kèm
                    theo trong Body:
    *               {
                        "recipeid":3,
                        "recipename":"Bánh Xèo Miền Tây",
                        "description":"Là 1 loại bánh ngon vl",
                        "calories":30,
                        "userId":"1",
                        "duration":120,
                        "image":"\\0",
                        "hidden":false

                    }
            Khi đó, Spring Boot sẽ tự động lôi từng thuộc tính ra (v.d. "recipename": Bành Xèo Miền Tây")
            sau đó đối chiếu xem, trong Recipe(Model) có thuộc tính nào tên là "recipename" không. Nếu có thì đem
            giá trị "Bánh Xèo Miền Tây" gài vào thuộc tính recipename, của Recipe(Model) đó.
        * */
    @Operation(
            description = "Create a Recipe of 1 User",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/recipe", method = RequestMethod.POST
            ,consumes = "multipart/form-data")
    public ResponseEntity createRecipe(@ModelAttribute CreateRecipeDTO newRec) {
        ResponseEntity result = null;
        Authentication authentication = null;
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            CreateRecipeCommand createRecipeCommand = new CreateRecipeCommand(newRec, userId);
            RecipeDTO recipeDTO = pipeline.send(createRecipeCommand);
            if (recipeDTO == null) {
                result = new ResponseEntity<String>("Couldn't add recipe. " +
                        "Please make sure that one of the fields (recipeName,description,image) is not null " +
                        "OR larger than 0 (calories, duration)",
                        HttpStatus.BAD_REQUEST);

            } else {
                result = new ResponseEntity<RecipeDTO>(recipeDTO, HttpStatus.OK);
            }
        } catch (InvalidDataExceptionHandler e) {
            result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    @Operation(
            description = "Updates the Recipe Details of 1 USER",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/recipe", method = RequestMethod.PUT)
    public ResponseEntity updateRecipeById(@RequestBody UpdateRecipeDTO rec) {
        ResponseEntity result = null;
        Authentication authentication = null;
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            UpdateRecipeCommand updateRecipeCommand = new UpdateRecipeCommand(rec,userId);
            RecipeDTO updatedRecipe = pipeline.send(updateRecipeCommand);
            if (updatedRecipe == null) {
                result = new ResponseEntity<String>("Something went wrong with the server and" +
                        " we couldn't add a recipe for you. Please try again.",
                        HttpStatus.BAD_REQUEST);
            } else {
                result = new ResponseEntity<String>("Successfully updated recipe with id " +
                        rec.getRecipeId(), HttpStatus.OK);
            }
        } catch (InvalidDataExceptionHandler e) {
            result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundExceptionHandler e) {
            result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    @Operation(
            description = "Deletes the Recipe Details of 1 USER",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/recipe/{recipeId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRecipeById(@PathVariable String recipeId) {
        ResponseEntity result = null;
        Authentication authentication = null;

        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            DeleteRecipeCommand command = new DeleteRecipeCommand(recipeId,userId);
            boolean isDeleted = pipeline.send(command);
            if (!isDeleted)
                result = new ResponseEntity<String>("Recipe with id " +
                        recipeId + " might be deleted or non-existent. Please try again.",
                        HttpStatus.BAD_REQUEST);
            else {
                result = new ResponseEntity<String>("Successfully deleted recipe with id " +
                        recipeId, HttpStatus.OK);
            }

        } catch (InvalidDataExceptionHandler e) {
            result = new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return result;
    }

}
