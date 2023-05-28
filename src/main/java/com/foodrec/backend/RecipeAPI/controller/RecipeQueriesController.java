package com.foodrec.backend.RecipeAPI.controller;

import com.foodrec.backend.RecipeAPI.dto.RecipeDto;
import com.foodrec.backend.RecipeAPI.model.Recipe;
import com.foodrec.backend.RecipeAPI.service.Query.RecipeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecipeQueriesController {
    /*tự động tạo 1 hiện thân (món đồ/vật thể/phần mềm/chiếc máy) chứa TẤT CẢ những hàm
    * có trong Interface _recipeQueryService. Nói cách khác, cái này sẽ tạo 1 cái máy RecipeQueryService,
    * chuyên xử lý nhiều thứ (v.d. kiểm tra dữ liệu xem có null không trước khi thêm vào database...)*/
    @Autowired
    private RecipeQueryService _recipeQueryService;

    //báo hiệu rằng hàm ngay dưới tương ứng với HttpGet - lấy dữ liệu + cách gọi nó.
    @RequestMapping(value="/recipe",method=RequestMethod.GET)
    /* ResponseEntity cơ bản là 1 HttpResponse
    (chứa status code (2xx,3xx,4xx,5xx); header, và body (chứa thông tin để trả cho Client)*/
    public ResponseEntity<ArrayList<RecipeDto>> getRecipes(){
        ArrayList<RecipeDto> result =  (ArrayList<RecipeDto>)_recipeQueryService.findAllRecipes();
        if(result==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }



    @RequestMapping(value="/recipe/{id}",method=RequestMethod.GET)
    /* @PathVariable String id: tìm xem, trong URI ở phần RequestMapping,
    *  có biến/thông tin nào có tên là {id} không. Nếu có thì lấy ra, không thì cook.

    * * Nguồn: https://www.baeldung.com/spring-requestmapping
    *        https://www.baeldung.com/spring-pathvariable*/
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable String id){
        RecipeDto result = _recipeQueryService.findRecipeByRecipeid(id);
        if(result==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
