package com.foodrec.backend.RecipeAPI.controller.command;


import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.RecipeAPI.command.create_recipe.CreateRecipeCommand;
import com.foodrec.backend.RecipeAPI.command.delete_recipe.DeleteRecipeCommand;
import com.foodrec.backend.RecipeAPI.command.update_recipe.UpdateRecipeCommand;
import com.foodrec.backend.RecipeAPI.dto.NewRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "RecipeAPI")
@RestController
public class RecipeCommandController {
    final Pipeline pipeline;
    public RecipeCommandController(Pipeline pipeline){
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
    @RequestMapping(value="/recipe",method= RequestMethod.POST)//cách để gọi hàm controller này.
    public ResponseEntity insertRecipe(@RequestBody NewRecipeDTO newRec){
        CreateRecipeCommand createRecipeCommand = new CreateRecipeCommand(newRec);
        boolean isInserted = pipeline.send(createRecipeCommand);
        if(isInserted==false)
            return new ResponseEntity<String>("Couldn't add recipe. Please make sure that NO FIELDS is null.",
                    HttpStatus.BAD_REQUEST);

        return new ResponseEntity<String>("Successfully added recipe with name "+
                newRec.getRecipename(),HttpStatus.OK);
    }


    @RequestMapping(value="/recipe",method=RequestMethod.PUT)
    public ResponseEntity<String> updateRecipeById(@RequestBody RUDRecipeDTO rec){
        UpdateRecipeCommand updateRecipeCommand = new UpdateRecipeCommand(rec);
        boolean isUpdated = pipeline.send(updateRecipeCommand);
        if(isUpdated==false)
            return new ResponseEntity<String>("One of the fields might be null, or the recipe is already deleted. Please try again.",
                    HttpStatus.BAD_REQUEST);

        return new ResponseEntity<String>("Successfully updated recipe with id "+
                rec.getRecipename(),HttpStatus.OK);
    }
    @RequestMapping(value="/recipe/{id}",method=RequestMethod.DELETE)
    public ResponseEntity updateRecipeHiddenById(@PathVariable String id){
        DeleteRecipeCommand command = new DeleteRecipeCommand(id);
        boolean isDeleted = pipeline.send(command);
        if(isDeleted==false)
            return new ResponseEntity<String>("Recipe with id "+
                    id+" might be deleted or non-existent. Please try again.",
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>("Successfully deleted recipe with id "+
                id,HttpStatus.OK);
    }

}
