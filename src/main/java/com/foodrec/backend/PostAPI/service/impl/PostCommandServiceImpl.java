package com.foodrec.backend.PostAPI.service.impl;

import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.PostAPI.service.PostCommandService;
import com.foodrec.backend.PostAPI.service.PostQueryService;
import com.github.dockerjava.api.exception.UnauthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostCommandServiceImpl implements PostCommandService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostQueryService postQueryService;

    @Override
    public boolean createPost(PostDTO postDTO) {
        boolean isSuccess = true;
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            postDTO.setTime(localDateTime);
            postDTO.setStatus(1);
            if (postDTO.getRecipeid() == null || postDTO.getUserid() == null) {
                throw new IllegalArgumentException("Invalid Post!");
            }
            //Convert PostDTO to Post entity
            Post post = modelMapper.map(postDTO, Post.class);
            // Check if the post already exists in the database using postid from postDTO
            Post existingPost = postRepository.findById(postDTO.getPostid()).orElse(null);
            if (existingPost != null) {
                return false; // post already exists, do not create a new one
            }
            //Save the new Post to the database using PostRepository
            postRepository.save(post);
            //Check the new Post
            if (postQueryService.findPostById(postDTO.getPostid()) == null) {
                return false;
            }
        } catch (IllegalAccessError illegalAccessError) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean updateStatusPost(String userid, String postid) {
        return false;
    }

    @Override
    public boolean removePostByUser(String userid, String postid) {
        boolean isRemoved = true;
        // Get the post DTO from the frontend
        PostDTO postDTO = postQueryService.findPostById(postid);
        if (postDTO == null) {
            throw new IllegalArgumentException("Invalid Post!");
        }
        //Convert PostDTO to Post entity
        Post post = modelMapper.map(postDTO, Post.class);
        //Check if the user is authorized to delete this post
        if (!post.getUserid().equals(userid)) {
            throw new UnauthorizedException("User is not authorized to delete this post!");
        }
        //Delete the post from the database using PostRepository
        try {
            postRepository.deleteById(postid);
        } catch (Exception e) {
            throw new RuntimeException("Could not delete post: " + e);
        }
        return isRemoved;
    }
}


