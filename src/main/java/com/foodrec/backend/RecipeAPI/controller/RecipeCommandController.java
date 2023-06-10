package com.foodrec.backend.RecipeAPI.controller;


import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.RecipeAPI.command.create_recipe.CreateRecipeCommand;
import com.foodrec.backend.RecipeAPI.command.delete_recipe.DeleteRecipeCommand;
import com.foodrec.backend.RecipeAPI.command.update_recipe.UpdateRecipeCommand;
import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.UpdateRecipeDTO;
import com.foodrec.backend.RecipeAPI.exceptions.InvalidRecipeAttributeException;
import com.foodrec.backend.RecipeAPI.exceptions.InvalidRecipeIdException;
import com.foodrec.backend.RecipeAPI.exceptions.RecipeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                        "userid":"1",
                        "duration":120,
                        "image":"\\0",
                        "hidden":false

                    }
            Khi đó, Spring Boot sẽ tự động lôi từng thuộc tính ra (v.d. "recipename": Bành Xèo Miền Tây")
            sau đó đối chiếu xem, trong Recipe(Model) có thuộc tính nào tên là "recipename" không. Nếu có thì đem
            giá trị "Bánh Xèo Miền Tây" gài vào thuộc tính recipename, của Recipe(Model) đó.
        * */
    @RequestMapping(value = "/recipe", method = RequestMethod.POST)//cách để gọi hàm controller này.
    public ResponseEntity createRecipe(@RequestBody CreateRecipeDTO newRec) {
        ResponseEntity result = null;
        try {

            CreateRecipeCommand createRecipeCommand = new CreateRecipeCommand(newRec);
            RecipeDTO recipeDTO = pipeline.send(createRecipeCommand);
            if (recipeDTO == null) {
                result = new ResponseEntity<String>("Couldn't add recipe. " +
                        "Please make sure that one of the fields (recipeName,description,image) is not null " +
                        "OR larger than 0 (calories, duration)",
                        HttpStatus.BAD_REQUEST);

            } else {
                result = new ResponseEntity<RecipeDTO>(recipeDTO, HttpStatus.OK);
            }
        } catch (InvalidRecipeIdException e) {
            result = new ResponseEntity<String>(e.getMsg(), HttpStatus.BAD_REQUEST);
        } catch (InvalidRecipeAttributeException e) {
            result = new ResponseEntity<String>(e.getMsg(), HttpStatus.BAD_REQUEST);

        }
        return result;
    }

    @RequestMapping(value = "/recipe", method = RequestMethod.PUT)
    public ResponseEntity updateRecipeById(@RequestBody UpdateRecipeDTO rec) {

        ResponseEntity result = null;
        try {
            UpdateRecipeCommand updateRecipeCommand = new UpdateRecipeCommand(rec);
            RecipeDTO updatedRecipe = pipeline.send(updateRecipeCommand);
            if (updatedRecipe == null) {
                result = new ResponseEntity<String>("Something went wrong with the server and" +
                        " we couldn't add a recipe for you. Please try again.",
                        HttpStatus.BAD_REQUEST);
            } else {
                result = new ResponseEntity<String>("Successfully updated recipe with id " +
                        rec.getRecipeId(), HttpStatus.OK);
            }
        } catch (InvalidRecipeIdException e) {
            result = new ResponseEntity<String>(e.getMsg(), HttpStatus.BAD_REQUEST);
        } catch (InvalidRecipeAttributeException e) {
            result = new ResponseEntity<String>(e.getMsg(), HttpStatus.BAD_REQUEST);
        } catch (RecipeNotFoundException e) {
            result = new ResponseEntity<String>(e.getMsg(), HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    @RequestMapping(value = "/recipe/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRecipeById(@PathVariable String id) {
        ResponseEntity result = null;
        try {
            DeleteRecipeCommand command = new DeleteRecipeCommand(id);
            boolean isDeleted = pipeline.send(command);
            if (isDeleted == false)
                result = new ResponseEntity<String>("Recipe with id " +
                        id + " might be deleted or non-existent. Please try again.",
                        HttpStatus.BAD_REQUEST);
            else {
                result = new ResponseEntity<String>("Successfully deleted recipe with id " +
                        id, HttpStatus.OK);
            }

        } catch (InvalidRecipeIdException e) {
            result = new ResponseEntity(e.getMsg(), HttpStatus.BAD_REQUEST);
        }
        return result;
    }

}
