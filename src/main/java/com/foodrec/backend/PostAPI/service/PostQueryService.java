package com.foodrec.backend.PostAPI.service;

import com.foodrec.backend.PostAPI.model.Post;

import java.util.List;

public interface PostQueryService {
    public List<Post> getAllPost();
}
