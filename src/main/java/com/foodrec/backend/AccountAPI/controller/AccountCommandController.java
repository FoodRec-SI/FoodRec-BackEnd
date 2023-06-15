package com.foodrec.backend.AccountAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.AccountAPI.command.create_account.CreateAccountCommand;
import com.foodrec.backend.AccountAPI.dto.CreateAccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
