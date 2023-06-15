package com.foodrec.backend.RecipeAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.query.get_recipe_by_id.GetRecipeByUserIdQuery;
import com.foodrec.backend.Exception.InvalidPageInfoException;
import com.foodrec.backend.Utils.GetCurrentUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "RecipeAPI")
@RestController
public class RecipeQueryController {

    //báo hiệu rằng hàm ngay dưới tương ứng với HttpGet - lấy dữ liệu + cách gọi nó.
    final Pipeline pipeline;

    public RecipeQueryController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }


    @Operation(description = "Gets all Recipes of 1 USER",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})

    @RequestMapping(value = "/api/member/recipe", method = RequestMethod.GET)
    public ResponseEntity getRecipesByUserId(@RequestParam(defaultValue = "0") String pageNumber,
                                                @RequestParam(defaultValue = "6") String pageSize) {
        ResponseEntity responseEntity = null;
        Authentication authentication = null;
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            String userid = GetCurrentUserData.getCurrentUserId(authentication);
            GetRecipeByUserIdQuery query = new GetRecipeByUserIdQuery(userid,pageNumber, pageSize);
            Page<RecipeDTO> result = pipeline.send(query);
            if (result == null) {
                return new ResponseEntity<>("Invalid Request. Please try again."
                        , HttpStatus.BAD_REQUEST);
            }
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);

        } catch (InvalidPageInfoException e) {
            responseEntity = new ResponseEntity(e.getMessage()
                    , HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

}
