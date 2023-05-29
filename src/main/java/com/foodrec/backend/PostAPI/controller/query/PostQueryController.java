package com.foodrec.backend.PostAPI.controller.query;

import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
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
    public ResponseEntity<List<PostDTO>> getAllPost(){
        List<PostDTO> postDTOs = postQueryService.getAllPosts();
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }
    @GetMapping("/posts/newest")
    public ResponseEntity<List<PostDTO>> sortNewestPostByDate() {
        try {
            List<PostDTO> postDTOs = postQueryService.sortNewestPostByDate();
            return new ResponseEntity<>(postDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}