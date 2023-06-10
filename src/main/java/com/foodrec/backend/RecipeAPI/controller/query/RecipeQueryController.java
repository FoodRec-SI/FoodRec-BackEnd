package com.foodrec.backend.RecipeAPI.controller.query;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.RecipeAPI.dto.DeleteRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.ReadRecipeDTO;
import com.foodrec.backend.RecipeAPI.exceptions.InvalidPageInfoException;
import com.foodrec.backend.RecipeAPI.exceptions.InvalidRecipeIdException;
import com.foodrec.backend.RecipeAPI.query.get_all.GetAllRecipesQuery;
import com.foodrec.backend.RecipeAPI.query.get_recipe_by_id.GetRecipeByIdQuery;
import org.modelmapper.internal.bytebuddy.pool.TypePool;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;

@RestController
public class RecipeQueryController {

    //báo hiệu rằng hàm ngay dưới tương ứng với HttpGet - lấy dữ liệu + cách gọi nó.
    final Pipeline pipeline;

    public RecipeQueryController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @RequestMapping(value = "/recipe", method = RequestMethod.GET)
    /* ResponseEntity cơ bản là 1 HttpResponse
    (chứa status code (2xx,3xx,4xx,5xx); header, và body (chứa thông tin để trả cho Client)*/
    public ResponseEntity<?> getRecipes(@RequestParam(defaultValue = "0")
                                        String pageNumber, @RequestParam(defaultValue = "6") String pageSize) {
        ResponseEntity responseEntity = null;
        try {
            GetAllRecipesQuery getAllRecipesQuery = new GetAllRecipesQuery(pageNumber, pageSize);
            Page<ReadRecipeDTO> result = pipeline.send(getAllRecipesQuery);
            if (result == null) {
                return new ResponseEntity<>("Invalid Request. Please try again."
                        , HttpStatus.BAD_REQUEST);
            }
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);

        } catch (InvalidPageInfoException e) {
            responseEntity = new ResponseEntity(e.getMsg()
                    , HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }


    @RequestMapping(value = "/recipe/{id}", method = RequestMethod.GET)
    /* @PathVariable String id: tìm xem, trong URI ở phần RequestMapping,
     *  có biến/thông tin nào có tên là {id} không. Nếu có thì lấy ra, không thì cook.

     * * Nguồn: https://www.baeldung.com/spring-requestmapping
     *        https://www.baeldung.com/spring-pathvariable*/
    public ResponseEntity<?> getRecipeById(@PathVariable String id) {
        ResponseEntity responseEntity = null;
        try {
            GetRecipeByIdQuery getRecipeByIdQuery = new GetRecipeByIdQuery(id);
            ReadRecipeDTO result = pipeline.send(getRecipeByIdQuery);
            if (result == null) {
                return new ResponseEntity<>("The recipe might be deleted or non-existent. " +
                        "Please try again."
                        , HttpStatus.BAD_REQUEST);
            }
            if (result != null) {
                responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (InvalidRecipeIdException e){
            responseEntity = new ResponseEntity<>(e.getMsg()
                    , HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
