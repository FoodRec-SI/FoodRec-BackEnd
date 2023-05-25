package com.foodrec.backend.PostAPI.controller.query;

import com.foodrec.backend.PostAPI.model.Post;
import com.foodrec.backend.PostAPI.service.PostQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostQueryController {
    @Autowired
    private PostQueryService postQueryService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPost(){
        List<Post> posts = postQueryService.getAllPost();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}