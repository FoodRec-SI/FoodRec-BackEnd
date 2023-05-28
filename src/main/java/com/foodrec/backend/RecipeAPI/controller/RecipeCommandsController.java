package com.foodrec.backend.RecipeAPI.controller;


import com.foodrec.backend.RecipeAPI.dto.RecipeDto;
import com.foodrec.backend.RecipeAPI.model.Recipe;
import com.foodrec.backend.RecipeAPI.service.Command.RecipeCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class RecipeCommandsController {
    @Autowired
    private RecipeCommandService _recipeCommandService;
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
    public ResponseEntity insertRecipe(@RequestBody RecipeDto newRec){
        boolean isInserted = _recipeCommandService.insertRecipe(newRec);
        //Kiểm tra xem công thức đã được thêm vào chưa.
        if(isInserted==false)
            return new ResponseEntity<String>("Couldn't add recipe.", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>("Successfully added recipe with id "+newRec.getRecipeid(),HttpStatus.OK);

    }


    @RequestMapping(value="/recipe/{id}",method=RequestMethod.PUT)
    public ResponseEntity<String> updateRecipeById(@RequestBody RecipeDto rec){
        boolean isUpdated = _recipeCommandService.updateRecipeDetailsById(rec);
        if(isUpdated==false)
            return new ResponseEntity<String>("Unable to update recipe with id, as one " +
                "of the fields (recipename,calories,userid,duration,image,hidden) might be null."+
                rec.getRecipeid(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<String>("Successfully updated recipe with id "+
                rec.getRecipeid(),HttpStatus.OK);
    }


    @RequestMapping(value="/recipe/{id}",method=RequestMethod.DELETE)
    public ResponseEntity deleteRecipeById(@PathVariable String id){
        boolean isDeleted = _recipeCommandService.deleteRecipeById(id);
        if(isDeleted==false)
            return new ResponseEntity<String>("Unable to delete recipe with id "+
                    id,HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>("Successfully deleted recipe with id "+
                id,HttpStatus.OK);
    }

}
