package com.foodrec.backend.PostAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.query.get_all_posts.GetAllPostsApprovedQuery;
import com.foodrec.backend.PostAPI.query.get_post_by_id.GetPostById;
import com.foodrec.backend.PostAPI.query.get_posts_by_recipe_name.GetPostsByRecipeNameQuery;
import com.foodrec.backend.PostAPI.query.get_posts_by_status_by_moderator.GetPostByStatusQuery;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "PostAPI")
@RestController
public class PostQueryController {

    final Pipeline pipeline;

    public PostQueryController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping("/api/member/posts")
    public ResponseEntity getAllPostsApproved(@RequestParam(defaultValue = "0") int pageNumber,
                                                             @RequestParam(defaultValue = "6") int pageSize) {
        try {
            GetAllPostsApprovedQuery query = new GetAllPostsApprovedQuery(pageNumber, pageSize);
            Page<PostDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/api/moderator/posts")
    public ResponseEntity<Page<PostDTO>> getAllPostsByStatus(@RequestParam(defaultValue = "0") int pageNumber,
                                                             @RequestParam(defaultValue = "6") int pageSize,
                                                             @RequestParam(required = true) List<PostStatus> postStatuses) {
        try {
            GetPostByStatusQuery query = new GetPostByStatusQuery(pageNumber, pageSize, postStatuses);
            Page<PostDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return new ResponseEntity<>(status);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/member/post/search")
    public ResponseEntity getPostsByRecipeName(@RequestParam(defaultValue = "0") int pageNumber,
                                               @RequestParam(defaultValue = "6") int pageSize,
                                               @RequestParam String recipeName) {
        try {
            GetPostsByRecipeNameQuery query = new GetPostsByRecipeNameQuery(pageNumber, pageSize, recipeName);
            Page<PostDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/api/member/{postId}")
    public ResponseEntity getPostById(@PathVariable String postId) {
        try {
            GetPostById query = new GetPostById(postId);
            PostDTO result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}