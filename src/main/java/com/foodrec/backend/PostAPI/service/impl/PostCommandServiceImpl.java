package com.foodrec.backend.PostAPI.service.impl;

import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.PostAPI.service.PostCommandService;
import com.foodrec.backend.PostAPI.service.PostQueryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
            if(existingPost != null){
                return false; // post already exists, do not create a new one
            }
            //Save the new Post entity to the database using PostRepository
            postRepository.save(post);
            //Check the new Post
            if(postQueryService.findPostByID(postDTO.getPostid())==null){
                return false;
            }
        } catch (IllegalAccessError illegalAccessError) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public void updatePost() {

    }

    @Override
    public void updateStatusPost() {

    }
}
