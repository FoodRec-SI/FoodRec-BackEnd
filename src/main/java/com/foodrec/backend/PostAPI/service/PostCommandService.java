package com.foodrec.backend.PostAPI.service;

import com.foodrec.backend.PostAPI.dto.PostDTO;

public interface PostCommandService {
    boolean createPost(PostDTO postDTO);

    void updatePost();

    void updateStatusPost();
}