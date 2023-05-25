package com.foodrec.backend.RecipeAPI.controller;

import com.foodrec.backend.RecipeAPI.model.Recipe;
import com.foodrec.backend.RecipeAPI.repository.IRecipeCommandsRepository;
import com.foodrec.backend.RecipeAPI.repository.IRecipeQueriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class RecipeController {
    /*tự động tạo 1 hiện thân (món đồ/vật thể/phần mềm/chiếc máy) chứa TẤT CẢ những hàm
    * có trong Interface IRecipeQueriesRepository.
     */
    @Autowired
    private IRecipeQueriesRepository _recipeQueriesRepository;
    @Autowired
    private IRecipeCommandsRepository _recipeCommandsRepository;

    //báo hiệu rằng hàm ngay dưới tương ứng với HttpGet - lấy dữ liệu + cách gọi nó.
    @RequestMapping(value="/recipe",method=RequestMethod.GET)
    /*ResponseEntity cơ bản là 1 HttpResponse
    (chứa status code (2xx,3xx,4xx,5xx); header, và body (chứa thông tin để trả cho Client)*/
    public ResponseEntity<ArrayList<Recipe>> getRecipes(){
        ArrayList<Recipe> result = (ArrayList<Recipe>) _recipeQueriesRepository.findAllRecipes();
        if(result==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ArrayList<Recipe>>(result,HttpStatus.OK);
    }
    @RequestMapping(value="/recipe/{id}",method=RequestMethod.GET)
    /* @PathVariable String id: tìm xem, trong URI ở phần RequestMapping,
    *  có biến/thông tin nào có tên là {id} không. Nếu có thì lấy ra, không thì cook.

    * * Nguồn: https://www.baeldung.com/spring-requestmapping
    *        https://www.baeldung.com/spring-pathvariable*/
    public ResponseEntity<Recipe> getRecipeById(@PathVariable String id){
        Recipe result = _recipeQueriesRepository.findRecipeByRecipeid(id);
        if(result==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Recipe>(result, HttpStatus.OK);
    }
     //báo hiệu rằng hàm ngay dưới tương ứng với HttpPost - thêm dữ liệu vào database.


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
    @RequestMapping(value="/recipe",method=RequestMethod.POST)//cách để gọi hàm controller này.
    public ResponseEntity insertRecipe(@RequestBody Recipe newRec){
        _recipeCommandsRepository.insertRecipe(newRec);
        return new ResponseEntity(HttpStatus.OK);

    }

}
