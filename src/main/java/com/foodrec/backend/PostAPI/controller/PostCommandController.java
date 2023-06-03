package com.foodrec.backend.PostAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.PostAPI.command.create_post.CreatePostCommand;
import com.foodrec.backend.PostAPI.command.delete_post.DeletePostCommand;
import com.foodrec.backend.PostAPI.command.update_post.UpdatePostCommand;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostCommandController {
    final Pipeline pipeline;

    public PostCommandController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @RequestMapping(value = "/post/{recipeid}/{userid}", method = RequestMethod.POST)
    public ResponseEntity<String> createPost(@RequestBody String recipeid, @RequestBody String userid) {
        CreatePostCommand command = new CreatePostCommand(recipeid, userid);
        boolean isSuccess = pipeline.send(command);
        if (isSuccess) {
            return ResponseEntity.ok("Post created successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid post data");
        }
    }

    @RequestMapping(value = "/post/{postid}/{userid}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removePostByUser(@PathVariable String postid, @PathVariable String userid) {
        DeletePostCommand command = new DeletePostCommand(postid, userid);
        boolean isRemoved = pipeline.send(command);
        if (isRemoved) {
            return ResponseEntity.ok("Post delete successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid post data");
        }
    }

    @RequestMapping(value = "/post/{postid}/{moderatorid}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<String> updatePostStatus(@PathVariable String postid, @PathVariable String moderatorid, @PathVariable int status) {
        UpdatePostCommand command = new UpdatePostCommand(postid, moderatorid, status);
        boolean isUpdated = pipeline.send(command);
        if (isUpdated) {
            return ResponseEntity.ok("Post status update successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid post data");
        }
    }
}
