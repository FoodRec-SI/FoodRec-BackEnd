package com.foodrec.backend.PostAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.PostAPI.dto.CombinedPostRecipeDTO;
import com.foodrec.backend.PostAPI.dto.ViewPostDTO;
import com.foodrec.backend.PostAPI.query.get_all_post_and_recipe.GetAllPostsAndRecipesQuery;
import com.foodrec.backend.PostAPI.query.get_all_posts.GetAllPostsQuery;
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
    public ResponseEntity<Page<ViewPostDTO>> getAllPost(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "6") int pageSize) {
        GetAllPostsQuery query = new GetAllPostsQuery(pageNumber, pageSize);
        Page<ViewPostDTO> result = pipeline.send(query);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/posts/recipe")
    public ResponseEntity<Page<CombinedPostRecipeDTO>> getAllPostsAndRecipes(@RequestParam(defaultValue = "0") int pageNumber,
                                                                             @RequestParam(defaultValue = "6") int pageSize) {
        GetAllPostsAndRecipesQuery query = new GetAllPostsAndRecipesQuery(pageNumber, pageSize);
        Page<CombinedPostRecipeDTO> result = pipeline.send(query);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}