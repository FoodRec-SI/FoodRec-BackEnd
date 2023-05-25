package com.foodrec.backend.PostAPI.service.impl;


import com.foodrec.backend.PostAPI.model.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.PostAPI.service.PostQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostQueryServiceImpl implements PostQueryService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }
}
