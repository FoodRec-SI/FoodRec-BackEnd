package com.foodrec.backend.AccountAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.AccountAPI.command.create_account.CreateAccountCommand;
import com.foodrec.backend.AccountAPI.command.update_account.UpdateAccountCommand;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;
import com.foodrec.backend.AccountAPI.dto.CreateAccountDTO;
import com.foodrec.backend.AccountAPI.dto.UpdateAccountDTO;
import com.foodrec.backend.Utils.GetCurrentUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "AccountAPI")
@RestController
public class AccountCommandController {

    final Pipeline pipeline;

    public AccountCommandController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(description = "Add User id and name to the database. This api will run in the background"
            ,security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping (value = "/api/member/account/create", method = RequestMethod.POST)
    public ResponseEntity createAccount(){
        CreateAccountDTO createAccountDTO = new CreateAccountDTO();
        CreateAccountCommand createAccountCommand = new CreateAccountCommand(createAccountDTO);
        HttpStatus status = pipeline.send(createAccountCommand);
        return new ResponseEntity<>(status);
    }

    @Operation(description = "Update account information"
            ,security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping (value = "/api/member/account/update", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public ResponseEntity updateAccount(@RequestBody UpdateAccountDTO updateAccountDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = GetCurrentUserData.getCurrentUserId(authentication);
        UpdateAccountCommand command = new UpdateAccountCommand(updateAccountDTO,userId);
        AccountDTO accountDTO = pipeline.send(command);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

}
