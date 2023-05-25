package com.foodrec.backend.RecipeAPI.controller;


import com.foodrec.backend.RecipeAPI.model.Recipe;
import com.foodrec.backend.RecipeAPI.repository.IRecipeCommandsRepository;
import com.foodrec.backend.RecipeAPI.repository.IRecipeQueriesRepository;
import com.foodrec.backend.RecipeAPI.service.ValidationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class RecipeCommandsController {
    @Autowired
    private IRecipeQueriesRepository _recipeQueriesRepository;
    @Autowired
    private IRecipeCommandsRepository _recipeCommandsRepository;
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
    @RequestMapping(value="/recipe",method= RequestMethod.POST)//cách để gọi hàm controller này.
    public ResponseEntity insertRecipe(@RequestBody Recipe newRec){
        _recipeCommandsRepository.insertRecipe(newRec);
        //Kiểm tra xem công thức đã được thêm vào chưa.
        Recipe foundRecipe = _recipeQueriesRepository.findRecipeByRecipeid(newRec.getRecipeid());
        if(foundRecipe==null){
            return new ResponseEntity<String>("Couldn't add recipe.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Successfully added recipe with id "+newRec.getRecipeid(),HttpStatus.OK);

    }
    @RequestMapping(value="/recipe/{id}",method=RequestMethod.DELETE)
    public ResponseEntity deleteRecipeById(@PathVariable String id){
        _recipeCommandsRepository.deleteRecipeById(id);
        Recipe foundRecipe = _recipeQueriesRepository.findRecipeByRecipeid(id);
        if(foundRecipe!=null){
            return new ResponseEntity<String>("Unable to delete recipe with id "+
                    id,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Successfully deleted recipe with id "+
                id,HttpStatus.OK);
    }

    @RequestMapping(value="/recipe/{id}",method=RequestMethod.PUT)
    public ResponseEntity<String> updateRecipeById(@RequestBody Recipe currentRecipe){
        try{
            ArrayList<String> excludedFields = new ArrayList<>
                    (Arrays.asList("recipeid","description"));
            boolean containsAnyEmptyField = ValidationServices.
                    containsAnyEmptyField(currentRecipe,excludedFields);

            if (containsAnyEmptyField==true){
                return new ResponseEntity<String>("Unable to update recipe with id, as one " +
                        "of the fields (recipename,calories,userid,duration,image,hidden) might be null."+
                        currentRecipe.getRecipeid(),HttpStatus.BAD_REQUEST);
            }
            _recipeCommandsRepository.updateRecipeDetailsById(currentRecipe);
        }catch(Exception e){

        }

        return new ResponseEntity<String>("Successfully updated recipe with id "+
                currentRecipe.getRecipeid(),HttpStatus.OK);
    }

}
