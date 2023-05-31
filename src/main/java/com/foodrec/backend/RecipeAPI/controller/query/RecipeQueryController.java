package com.foodrec.backend.RecipeAPI.controller.query;

import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;
import com.foodrec.backend.RecipeAPI.service.RecipeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class RecipeQueryController {
    /*tự động tạo 1 hiện thân (món đồ/vật thể/phần mềm/chiếc máy) chứa TẤT CẢ những hàm
    * có trong Interface recipeQueryService. Nói cách khác, cái này sẽ tạo 1 cái máy RecipeQueryService,
    * chuyên xử lý nhiều thứ (v.d. kiểm tra dữ liệu xem có null không trước khi thêm vào database...)*/
    @Autowired
    private RecipeQueryService recipeQueryService;

    //báo hiệu rằng hàm ngay dưới tương ứng với HttpGet - lấy dữ liệu + cách gọi nó.
    @RequestMapping(value="/recipe",method=RequestMethod.GET)
    /* ResponseEntity cơ bản là 1 HttpResponse
    (chứa status code (2xx,3xx,4xx,5xx); header, và body (chứa thông tin để trả cho Client)*/
    public ResponseEntity<?> getRecipes(){
        ArrayList<RUDRecipeDTO> result =  (ArrayList<RUDRecipeDTO>)
                recipeQueryService.findAllRecipes();
        if(result==null){
            return new ResponseEntity<>("Invalid Request. Please try again."
                    ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }



    @RequestMapping(value="/recipe/{id}",method=RequestMethod.GET)
    /* @PathVariable String id: tìm xem, trong URI ở phần RequestMapping,
    *  có biến/thông tin nào có tên là {id} không. Nếu có thì lấy ra, không thì cook.

    * * Nguồn: https://www.baeldung.com/spring-requestmapping
    *        https://www.baeldung.com/spring-pathvariable*/
    public ResponseEntity<?> getRecipeById(@PathVariable String id){
        RUDRecipeDTO result = recipeQueryService.findRecipeByRecipeid(id);
        if(result==null){
            return new ResponseEntity<>("The reipe might not be added yet. Please try again."
                    ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
