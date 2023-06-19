package com.foodrec.backend.AccountAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;
import com.foodrec.backend.AccountAPI.query.get_account_by_id.GetAccountByIdQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "AccountAPI")
@RestController
public class AccountQueryController {

    final Pipeline pipeline;

    public AccountQueryController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(description = "Get an account information by its id. Must provide UserId",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/member/account/{userId}")
    public ResponseEntity getAllCollectionByUserId(@PathVariable String userId) {
        GetAccountByIdQuery getAccountByIdQuery = new GetAccountByIdQuery(userId);
        AccountDTO accountDTO = pipeline.send(getAccountByIdQuery);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }
}
