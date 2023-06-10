package com.foodrec.backend.PostAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.query.get_all_posts.GetAllPostsApprovedQuery;
import com.foodrec.backend.PostAPI.query.get_posts_by_status_by_moderator.GetPostByStatusQuery;
import com.foodrec.backend.exception.DuplicateExceptionHandler;
import com.foodrec.backend.exception.InvalidDataExceptionHandler;
import com.foodrec.backend.exception.UnauthorizedExceptionHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        try {
            GetAllPostsApprovedQuery query = new GetAllPostsApprovedQuery(pageNumber, pageSize);
            Page<PostDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return new ResponseEntity<>(status);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/moderator/posts")
    public ResponseEntity<Page<PostDTO>> getAllPostsByStatus(@RequestParam(defaultValue = "0") int pageNumber,
                                                             @RequestParam(defaultValue = "6") int pageSize,
                                                             @RequestParam(required = true) List<PostStatus> postStatuses) {
        try {
            GetPostByStatusQuery query = new GetPostByStatusQuery(pageNumber, pageSize, postStatuses);
            System.out.println(query.getPostStatuses());
            Page<PostDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return new ResponseEntity<>(status);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}