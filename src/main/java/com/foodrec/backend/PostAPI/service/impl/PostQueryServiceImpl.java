package com.foodrec.backend.PostAPI.service.impl;


import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.PostAPI.service.PostQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostQueryServiceImpl implements PostQueryService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PostDTO findPostById(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return modelMapper.map(post, PostDTO.class);
        } else {
            throw new EntityNotFoundException("Post not found with id: " + postId);
        }
    }

    @Override
    public List<PostDTO> sortNewestPostByDate() {
        List<PostDTO> posts = getAllPosts(); // assume getAllPosts() returns all posts
        Collections.sort(posts, Comparator.comparing(PostDTO::getTime).reversed());
        return posts;
    }

}
