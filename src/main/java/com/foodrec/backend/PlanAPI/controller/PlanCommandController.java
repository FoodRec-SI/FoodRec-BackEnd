package com.foodrec.backend.PlanAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.DuplicateExceptionHandler;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.PlanAPI.command.create_base_plan.CreateBasePlanCommand;
import com.foodrec.backend.PlanAPI.command.remove_plan.RemovePlanCommand;
import com.foodrec.backend.PlanAPI.command.update_full_plan.UpdateFullPlanCommand;
import com.foodrec.backend.PlanAPI.dto.*;
import com.foodrec.backend.Utils.GetCurrentUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "PlanAPI")
@RestController
public class PlanCommandController {
    final Pipeline pipeline;

    public PlanCommandController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(description = "Create a plan with BASIC information (Plan Name, Description, Date of Creation." +
            " This is for the FIRST STEP of the Meal Planning procedure.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/plan", method = RequestMethod.POST)
    public ResponseEntity createBasePlan(@RequestBody CreateBasePlanDTO createBasePlanDTO) {
        ResponseEntity responseEntity = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            CreateBasePlanCommand createBasePlanCommand = new CreateBasePlanCommand(userId, createBasePlanDTO);
            BasePlanDTO result = pipeline.send(createBasePlanCommand);
            if (result!=null) responseEntity = new ResponseEntity<>(result,HttpStatus.OK);

        } catch (InvalidDataExceptionHandler | DuplicateExceptionHandler | UnauthorizedExceptionHandler |
                 NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error!");
        }
        return responseEntity;
    }

    @Operation(description = "Remove the previously created plan with BASIC information (name, description, date of creation)" +
            " This is used when the user no longer wants to add more Meals to the Plan.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/plan", method = RequestMethod.DELETE)
    public ResponseEntity removePlan(@RequestBody RemovePlanDTO removePlanDTO) {
        ResponseEntity responseEntity = null;
        try {
            RemovePlanCommand removePlanCommand = new RemovePlanCommand(removePlanDTO);
            Boolean result = pipeline.send(removePlanCommand);
            if (result==true) responseEntity = new ResponseEntity<>(
                    "Successfully removed plan with Id" + removePlanCommand.getRemovePlanDTO().getPlanId(),HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | DuplicateExceptionHandler | UnauthorizedExceptionHandler |
                 NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error!");
        }
        return responseEntity;
    }

    @Operation(description = "Updates the already existing Plan with full details" +
            "(ingredient-list, meal-quantity, calories)",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/plan", method = RequestMethod.PUT)
    public ResponseEntity updateFullPlan(@RequestBody UpdateFullPlanDTO updateFullPlanDTO) {
        ResponseEntity responseEntity = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            UpdateFullPlanCommand updateFullPlanCommand =
                    new UpdateFullPlanCommand(userId,updateFullPlanDTO);
            FullPlanDTO result = pipeline.send(updateFullPlanCommand);
            if (result!=null) responseEntity = new ResponseEntity<>(result,HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | DuplicateExceptionHandler | UnauthorizedExceptionHandler |
                 NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error!");
        }
        return responseEntity;
    }

}
