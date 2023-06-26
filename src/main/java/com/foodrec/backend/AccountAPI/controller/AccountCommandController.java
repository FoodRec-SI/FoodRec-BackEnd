package com.foodrec.backend.AccountAPI.controller;

import an.awesome.pipelinr.Pipeline;

import com.foodrec.backend.AccountAPI.command.create_account.CreateAccountCommand;
import com.foodrec.backend.AccountAPI.command.delete_account.DeleteAccountCommand;
import com.foodrec.backend.AccountAPI.command.update_account.UpdateAccountCommand;
import com.foodrec.backend.AccountAPI.command.account_tags.AccountTagsCommand;
import com.foodrec.backend.AccountAPI.dto.*;
import com.foodrec.backend.Utils.GetCurrentUserData;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;
import com.foodrec.backend.AccountAPI.dto.CreateAccountDTO;
import com.foodrec.backend.AccountAPI.dto.UpdateAccountDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

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

    @Operation(description = "Add user information to the database. This api will run in the background"
            , security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/private/account/create", method = RequestMethod.GET)
    public ResponseEntity createAccount() {
        CreateAccountDTO createAccountDTO = new CreateAccountDTO();
        CreateAccountCommand createAccountCommand = new CreateAccountCommand(createAccountDTO);
        ResponseEntity responseEntity = pipeline.send(createAccountCommand);
        return responseEntity;
    }

    @Operation(description = "Modify user tags. This endpoint can be used for add, update, and delete tags"
            , security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/account/tags", method = RequestMethod.POST)
    public ResponseEntity updateAccountTags(@RequestParam Set<String> tagIds) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = GetCurrentUserData.getCurrentUserId(authentication);
        AccountTagsCommand accountTagsCommand = new AccountTagsCommand(tagIds, userId);
        HttpStatus status = pipeline.send(accountTagsCommand);
        return new ResponseEntity<>(status);
    }

    @Operation(description = "Update account information"
            , security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/account/update", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public ResponseEntity updateAccount(@ModelAttribute UpdateAccountDTO updateAccountDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = GetCurrentUserData.getCurrentUserId(authentication);
        UpdateAccountCommand command = new UpdateAccountCommand(updateAccountDTO, userId);
        AccountDTO accountDTO = pipeline.send(command);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @Operation(description = "Delete account information"
            , security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/account/delete", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity deleteAccount(@RequestBody DeleteAccountDTO deleteAccountDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = GetCurrentUserData.getCurrentUserId(authentication);
        DeleteAccountCommand command = new DeleteAccountCommand(deleteAccountDTO, userId);
        HttpStatus status = pipeline.send(command);
        return new ResponseEntity<>(status);
    }

}
