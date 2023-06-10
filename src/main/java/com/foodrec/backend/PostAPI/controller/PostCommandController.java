package com.foodrec.backend.PostAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.PostAPI.command.create_post.CreatePostCommand;
import com.foodrec.backend.PostAPI.command.delete_post.DeletePostCommand;
import com.foodrec.backend.PostAPI.command.update_post.UpdatePostCommand;
import com.foodrec.backend.PostAPI.dto.CreatePostDTO;
import com.foodrec.backend.PostAPI.dto.DeletePostDTO;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.dto.UpdatePostDTO;
import com.foodrec.backend.exception.DuplicateExceptionHandler;
import com.foodrec.backend.exception.InvalidDataExceptionHandler;
import com.foodrec.backend.exception.NotFoundExceptionHandler;
import com.foodrec.backend.exception.UnauthorizedExceptionHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PostAPI")
@RestController
public class PostCommandController {
    final Pipeline pipeline;

    public PostCommandController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @RequestMapping(value = "/api/member/post", method = RequestMethod.POST)
    public ResponseEntity createPost(@RequestBody CreatePostDTO createPostDTO) {
        ResponseEntity responseEntity = null;
        try {
            CreatePostCommand command = new CreatePostCommand(createPostDTO);
            PostDTO postDTO = pipeline.send(command);
            responseEntity = new ResponseEntity<>(postDTO, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | DuplicateExceptionHandler | UnauthorizedExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error!");
        }
        return responseEntity;
    }

    @RequestMapping(value = "/api/moderator/post", method = RequestMethod.DELETE)
    public ResponseEntity<String> removePostByUser(@RequestBody DeletePostDTO deletePostDTO) {
        try {
            DeletePostCommand command = new DeletePostCommand(deletePostDTO);
            boolean isRemoved = pipeline.send(command);
            if (isRemoved) {
                return ResponseEntity.ok("Post delete successfully!");
            } else {
                return ResponseEntity.badRequest().body("Invalid post data");
            }
        } catch (InvalidDataExceptionHandler | DuplicateExceptionHandler | UnauthorizedExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error!");
        }
    }

    @RequestMapping(value = "/api/member/post", method = RequestMethod.PUT)
    public ResponseEntity updatePostStatus(@RequestBody UpdatePostDTO updatePostDTO) {
        ResponseEntity responseEntity = null;
        try {
            UpdatePostCommand command = new UpdatePostCommand(updatePostDTO);
            PostDTO postDTO = pipeline.send(command);
            responseEntity = new ResponseEntity<>(postDTO, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler | UnauthorizedExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
        return responseEntity;
    }
}
