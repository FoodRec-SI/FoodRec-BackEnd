package com.foodrec.backend.PlanAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.DuplicateExceptionHandler;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.PlanAPI.dto.DateDTO;
import com.foodrec.backend.PlanAPI.dto.ReadBasicPlanDTO;
import com.foodrec.backend.PlanAPI.dto.ReadFullPlanDTO;
import com.foodrec.backend.PlanAPI.query.get_plan_by_date.GetPlansBetweenDatesQuery;
import com.foodrec.backend.PlanAPI.query.get_plan_by_id.GetPlanByIdQuery;
import com.foodrec.backend.PlanAPI.query.ingredient_list_generate.IngredientListGeneratorQuery;
import com.foodrec.backend.Utils.GetCurrentUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "PlanAPI")
@RestController
public class PlanQueryController {
    final Pipeline pipeline;

    public PlanQueryController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(description = "Gets the plan list given From and To Date.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/plan/list", method = RequestMethod.POST)
    public ResponseEntity getPlansBetweenDate(@RequestBody DateDTO dateDTO) {
        ResponseEntity responseEntity = null;
        try {
            GetPlansBetweenDatesQuery getPlansBetweenDatesQuery =
                    new GetPlansBetweenDatesQuery(dateDTO.getStartDate(), dateDTO.getEndDate());
            List<ReadBasicPlanDTO> result = pipeline.send(getPlansBetweenDatesQuery);
            if (result != null) responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | DuplicateExceptionHandler | UnauthorizedExceptionHandler |
                 NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return responseEntity;
    }

    @Operation(description = "Gets the full detail of a Plan by its Id (list of Meals, and List of Posts within each Meal",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/plan/{planId}", method = RequestMethod.GET)
    public ResponseEntity getPlanById(@PathVariable String planId) {
        ResponseEntity responseEntity = null;
        try {
            GetPlanByIdQuery getPlanByIdQuery =
                    new GetPlanByIdQuery(planId);
            ReadFullPlanDTO result = pipeline.send(getPlanByIdQuery);
            if (result != null) responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | DuplicateExceptionHandler | UnauthorizedExceptionHandler |
                 NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return responseEntity;
    }

    @Operation(description = "Get ingredient list of a plan by Plan Id",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/plan/ingredient/{planId}", method = RequestMethod.GET)
    public ResponseEntity getIngredientList(@PathVariable String planId) {
        ResponseEntity responseEntity;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            IngredientListGeneratorQuery query = new IngredientListGeneratorQuery(planId, userId);
            String result = pipeline.send(query);
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NotFoundExceptionHandler | UnauthorizedExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return responseEntity;
    }
}
