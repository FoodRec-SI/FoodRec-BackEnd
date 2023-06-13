//package com.foodrec.backend.RecipeAPI.controller;
//
//import an.awesome.pipelinr.Pipeline;
//import com.foodrec.backend.RecipeAPI.command.create_recipe.CreateRecipeCommand;
//import com.foodrec.backend.RecipeAPI.command.delete_recipe.DeleteRecipeCommand;
//import com.foodrec.backend.RecipeAPI.command.update_recipe.UpdateRecipeCommand;
//import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;
//import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
//import com.foodrec.backend.RecipeAPI.dto.UpdateRecipeDTO;
//import com.foodrec.backend.RecipeAPI.exceptions.InvalidRecipeAttributeException;
//import com.foodrec.backend.RecipeAPI.exceptions.InvalidRecipeIdException;
//import com.foodrec.backend.RecipeAPI.exceptions.RecipeNotFoundException;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;
//
//@Tag(name = "RecipeAPI")
//@RestController
//public class RecipeCommandController {
//    final Pipeline pipeline;
//
//    public RecipeCommandController(Pipeline pipeline) {
//        this.pipeline = pipeline;
//    }
//
//    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
//    @RequestMapping(value = "/api/member/recipe", method = RequestMethod.POST)
//    public ResponseEntity createRecipe(@RequestBody CreateRecipeDTO newRec) {
//        ResponseEntity result = null;
//        try {
//            CreateRecipeCommand createRecipeCommand = new CreateRecipeCommand(newRec);
//            RecipeDTO recipeDTO = pipeline.send(createRecipeCommand);
//            if (recipeDTO == null) {
//                result = new ResponseEntity<String>("Couldn't add recipe. " +
//                        "Please make sure that one of the fields (recipeName,description,image) is not null " +
//                        "OR larger than 0 (calories, duration)",
//                        HttpStatus.BAD_REQUEST);
//            } else {
//                result = new ResponseEntity<RecipeDTO>(recipeDTO, HttpStatus.OK);
//            }
//        } catch (InvalidRecipeIdException e) {
//            result = new ResponseEntity<String>(e.getMsg(), HttpStatus.BAD_REQUEST);
//        } catch (InvalidRecipeAttributeException e) {
//            result = new ResponseEntity<String>(e.getMsg(), HttpStatus.BAD_REQUEST);
//        }
//        return result;
//    }
//
//    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
//    @RequestMapping(value = "/api/member/recipe", method = RequestMethod.PUT)
//    public ResponseEntity updateRecipeById(@RequestBody UpdateRecipeDTO rec) {
//        ResponseEntity result = null;
//        try {
//            UpdateRecipeCommand updateRecipeCommand = new UpdateRecipeCommand(rec);
//            RecipeDTO updatedRecipe = pipeline.send(updateRecipeCommand);
//            if (updatedRecipe == null) {
//                result = new ResponseEntity<String>("Something went wrong with the server and" +
//                        " we couldn't add a recipe for you. Please try again.",
//                        HttpStatus.BAD_REQUEST);
//            } else {
//                result = new ResponseEntity<String>("Successfully updated recipe with id " +
//                        rec.getRecipeId(), HttpStatus.OK);
//            }
//        } catch (InvalidRecipeIdException e) {
//            result = new ResponseEntity<String>(e.getMsg(), HttpStatus.BAD_REQUEST);
//        } catch (InvalidRecipeAttributeException e) {
//            result = new ResponseEntity<String>(e.getMsg(), HttpStatus.BAD_REQUEST);
//        } catch (RecipeNotFoundException e) {
//            result = new ResponseEntity<String>(e.getMsg(), HttpStatus.BAD_REQUEST);
//        }
//        return result;
//    }
//
//    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
//    @RequestMapping(value = "/api/member/recipe/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity deleteRecipeById(@PathVariable String id) {
//        ResponseEntity result = null;
//        try {
//            DeleteRecipeCommand command = new DeleteRecipeCommand(id);
//            boolean isDeleted = pipeline.send(command);
//            if (isDeleted == false)
//                result = new ResponseEntity<String>("Recipe with id " +
//                        id + " might be deleted or non-existent. Please try again.",
//                        HttpStatus.BAD_REQUEST);
//            else {
//                result = new ResponseEntity<String>("Successfully deleted recipe with id " +
//                        id, HttpStatus.OK);
//            }
//
//        } catch (InvalidRecipeIdException e) {
//            result = new ResponseEntity(e.getMsg(), HttpStatus.BAD_REQUEST);
//        }
//        return result;
//    }
//}