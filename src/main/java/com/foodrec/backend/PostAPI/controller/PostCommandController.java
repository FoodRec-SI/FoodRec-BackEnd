package com.foodrec.backend.PostAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.PostAPI.command.create_post.CreatePostCommand;
import com.foodrec.backend.PostAPI.command.delete_post.DeletePostCommand;
import com.foodrec.backend.PostAPI.command.update_post.UpdatePostCommand;
import com.foodrec.backend.PostAPI.dto.CreatePostDTO;
import com.foodrec.backend.PostAPI.dto.DeletePostDTO;
import com.foodrec.backend.PostAPI.dto.UpdatePostDTO;
import com.foodrec.backend.exception.DuplicateExceptionHandler;
import com.foodrec.backend.exception.InvalidDataExceptionHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PostAPI")
@RestController
public class PostCommandController {
    final Pipeline pipeline;

    public PostCommandController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ResponseEntity<String> createPost(@RequestBody CreatePostDTO createPostDTO) throws Exception {
        try {
            CreatePostCommand command = new CreatePostCommand(createPostDTO);
            boolean isCreated = pipeline.send(command);
            if (isCreated) {
                return ResponseEntity.ok("Post created successfully!");
            } else {
                return ResponseEntity.badRequest().body("Invalid post data");
            }
        } catch (InvalidDataExceptionHandler | DuplicateExceptionHandler ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @RequestMapping(value = "/post", method = RequestMethod.DELETE)
    public ResponseEntity<String> removePostByUser(@RequestBody DeletePostDTO deletePostDTO) {
        DeletePostCommand command = new DeletePostCommand(deletePostDTO);
        boolean isRemoved = pipeline.send(command);
        if (isRemoved) {
            return ResponseEntity.ok("Post delete successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid post data");
        }
    }

    @RequestMapping(value = "/post", method = RequestMethod.PUT)
    public ResponseEntity<String> updatePostStatus(@RequestBody UpdatePostDTO updatePostDTO) {
        UpdatePostCommand command = new UpdatePostCommand(updatePostDTO);
        boolean isUpdated = pipeline.send(command);
        if (isUpdated) {
            return ResponseEntity.ok("Post status update successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid post data");
        }
    }
}
