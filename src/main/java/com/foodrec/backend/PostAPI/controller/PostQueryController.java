package com.foodrec.backend.PostAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.query.get_all_posts.GetAllPostsApprovedQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "PostAPI")
@RestController
public class PostQueryController {

    final Pipeline pipeline;

    public PostQueryController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostDTO>> getAllPostsApproved(@RequestParam(defaultValue = "0") int pageNumber,
                                                             @RequestParam(defaultValue = "6") int pageSize) {
        GetAllPostsApprovedQuery query = new GetAllPostsApprovedQuery(pageNumber, pageSize);
        Page<PostDTO> result = pipeline.send(query);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}