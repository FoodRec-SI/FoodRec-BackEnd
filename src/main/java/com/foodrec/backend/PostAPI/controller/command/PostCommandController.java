package com.foodrec.backend.PostAPI.controller.command;

import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.service.PostCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostCommandController {
    @Autowired
    private PostCommandService postCommandService;

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ResponseEntity<String> createPost(@RequestBody PostDTO postDTO) {
        boolean isSuccess = postCommandService.createPost(postDTO);
        if (isSuccess) {
            return new ResponseEntity<>("Post created successfully!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create post!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> removePostByUser(@PathVariable String postId) {
        // Get the user ID from the authenticated principal
        String userId = "ACC001";
        boolean isRemoved = postCommandService.removePostByUser(userId, postId);
        if (isRemoved) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Post removed successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to remove post!");
        }

    }
}

