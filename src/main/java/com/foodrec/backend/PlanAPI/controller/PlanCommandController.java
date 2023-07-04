package com.foodrec.backend.PlanAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.DuplicateExceptionHandler;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.PlanAPI.command.create_base_plan.CreateBasePlanCommand;
import com.foodrec.backend.PlanAPI.command.remove_base_plan.RemoveBasePlanCommand;
import com.foodrec.backend.PlanAPI.dto.BasePlanDTO;
import com.foodrec.backend.PlanAPI.dto.ReadBasePlanDTO;
import com.foodrec.backend.PlanAPI.dto.RemoveBasePlanDTO;
import com.foodrec.backend.PostAPI.command.create_post.CreatePostCommand;
import com.foodrec.backend.PostAPI.dto.CreatePostDTO;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.Utils.GetCurrentUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Controller
public class PlanCommandController {
    final Pipeline pipeline;

    public PlanCommandController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(description = "Create a plan with BASIC information (Plan Name, Description, Date of Creation." +
            " This is for the FIRST STEP of the Meal Planning procedure.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/plan/base", method = RequestMethod.POST)
    public ResponseEntity createBasePlan(@RequestBody BasePlanDTO basePlanDTO) {
        ResponseEntity responseEntity = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            CreateBasePlanCommand createBasePlanCommand = new CreateBasePlanCommand(userId,basePlanDTO);
            ReadBasePlanDTO result = pipeline.send(createBasePlanCommand);
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
    @RequestMapping(value = "/api/member/plan/base", method = RequestMethod.DELETE)
    public ResponseEntity removeBasePlan(@RequestBody RemoveBasePlanDTO removeBasePlanDTO) {
        ResponseEntity responseEntity = null;
        try {
            RemoveBasePlanCommand removeBasePlanCommand = new RemoveBasePlanCommand(removeBasePlanDTO);
            Boolean result = pipeline.send(removeBasePlanCommand);
            if (result==true) responseEntity = new ResponseEntity<>(
                    "Successfully removed plan with Id" + removeBasePlanCommand.getRemoveBasePlanDTO().getPlanId(),HttpStatus.OK);
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
