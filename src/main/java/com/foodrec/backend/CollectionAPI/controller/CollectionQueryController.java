package com.foodrec.backend.CollectionAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.CollectionAPI.dto.CollectionDTO;
import com.foodrec.backend.CollectionAPI.query.get_collections_by_user_id.GetCollectionsByUserIdQuery;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Utils.GetCurrentUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "CollectionAPI")
@RestController
public class CollectionQueryController {
    final Pipeline pipeline;

    public CollectionQueryController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(description = "Get all collection by userid from database.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/member/collections")
    public ResponseEntity getAllCollectionByUserId(@RequestParam(defaultValue = "0") int pageNumber,
                                                   @RequestParam(defaultValue = "6") int pageSize) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            GetCollectionsByUserIdQuery query = new GetCollectionsByUserIdQuery(pageNumber, pageSize, userId);
            Page<CollectionDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return new ResponseEntity<>(status);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
