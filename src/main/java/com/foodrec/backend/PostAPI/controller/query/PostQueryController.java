package com.foodrec.backend.PostAPI.controller.query;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.query.get_all_posts.GetAllPostsQuery;
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
    Pipeline pipeline;
    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPost(){
        List<PostDTO> result = pipeline.send(new GetAllPostsQuery());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}