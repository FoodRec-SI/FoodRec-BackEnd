package com.foodrec.backend.CollectionAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.CollectionAPI.command.create_collection.CreateCollectionCommand;
import com.foodrec.backend.CollectionAPI.command.delete_collection.DeleteCollectionCommand;
import com.foodrec.backend.CollectionAPI.command.upate_collection.add_post_to_collection.AddPostCommand;
import com.foodrec.backend.CollectionAPI.command.upate_collection.remove_post_from_collection.RemovePostCommand;
import com.foodrec.backend.CollectionAPI.command.upate_collection.update_data_collection.UpdateCollectionCommand;
import com.foodrec.backend.CollectionAPI.dto.*;
import com.foodrec.backend.Exception.DuplicateExceptionHandler;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.Utils.GetCurrentUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "CollectionAPI")
@RestController
public class CollectionCommandController {
    final Pipeline pipeline;

    public CollectionCommandController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(description = "Create new collection.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/collection", method = RequestMethod.POST)
    public ResponseEntity createCollection(@RequestBody CreateCollectionDTO createCollectionDTO) {
        ResponseEntity responseEntity = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            CreateCollectionCommand command = new CreateCollectionCommand(createCollectionDTO, userId);
            HttpStatus status = pipeline.send(command);
            responseEntity = ResponseEntity.status(status).body("Create collection successfully!");
        } catch (InvalidDataExceptionHandler | DuplicateExceptionHandler | UnauthorizedExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return responseEntity;
    }

    @Operation(description = "Remove post in collection. Must give collectionID and postID!",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/collections/posts", method = RequestMethod.DELETE)
    public ResponseEntity removePostToCollection(@RequestBody RemovePostCollectionDTO removePostCollectionDTO) {
        ResponseEntity responseEntity = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            RemovePostCommand command = new RemovePostCommand(removePostCollectionDTO, userId);
            HttpStatus status = pipeline.send(command);
            responseEntity = ResponseEntity.status(status).body("Remove post " + removePostCollectionDTO.getPostId() +
                    " to collection " + removePostCollectionDTO.getCollectionId() + " successfully!");
        } catch (InvalidDataExceptionHandler | UnauthorizedExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return responseEntity;
    }

    @Operation(description = "Delete collection by collection ID. Must give collection ID!",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/collection", method = RequestMethod.DELETE)
    public ResponseEntity deleteCollection(@RequestBody DeleteCollectionDTO deleteCollectionDTO) {
        ResponseEntity responseEntity = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            DeleteCollectionCommand command = new DeleteCollectionCommand(deleteCollectionDTO, userId);
            HttpStatus status = pipeline.send(command);
            responseEntity = ResponseEntity.status(status).body("Delete collection successfully!");
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler | UnauthorizedExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return responseEntity;
    }

    @Operation(description = "Update collection by collection ID. Must give collection ID!",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/collection", method = RequestMethod.PUT)
    public ResponseEntity updateCollection(@RequestBody UpdateCollectionDTO updateCollectionDTO) {
        ResponseEntity responseEntity = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            UpdateCollectionCommand command = new UpdateCollectionCommand(updateCollectionDTO, userId);
            HttpStatus status = pipeline.send(command);
            responseEntity = ResponseEntity.status(status).body("Update collection successfully!");
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler | UnauthorizedExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return responseEntity;
    }

    @Operation(description = "Add new post to collection. Must give postID and collectionID!",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/collections/posts", method = RequestMethod.PUT)
    public ResponseEntity addPostToCollection(@RequestBody PostCollectionDTO postCollectionDTO) {
        ResponseEntity responseEntity = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            AddPostCommand command = new AddPostCommand(postCollectionDTO, userId);
            HttpStatus status = pipeline.send(command);
            responseEntity = ResponseEntity.status(status).body("Add post to collection successfully!");
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler | UnauthorizedExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return responseEntity;
    }
}
