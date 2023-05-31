package com.foodrec.backend.PostAPI.service;

import com.foodrec.backend.PostAPI.dto.PostDTO;

import java.util.List;

public interface PostQueryService {
    List<PostDTO> getAllPosts();
    PostDTO findPostById(String postid);
    List<PostDTO> sortNewestPostByDate();

}
