package com.foodrec.backend.PostAPI.command.create_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CreatePostCommandHandler implements Command.Handler<CreatePostCommand, Boolean> {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public CreatePostCommandHandler(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean handle(CreatePostCommand command) {
        PostDTO postDTO = command.getPostDTO();
        boolean isCreated = true;
        try {
            if (postDTO.getRecipeid() == null || postDTO.getUserid() == null) {
                throw new IllegalArgumentException("Invalid Post!");
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            postDTO.setTime(localDateTime);
            postDTO.setStatus(1); // Set status is pending censorship
            List<Post> existingPost = postRepository.findAll();
            for (Post value : existingPost) {
                if (value.getRecipeid().equals(postDTO.getRecipeid())) {
                    throw new IllegalArgumentException("Duplicate RecipeID!");
                }
            }
            //Convert PostDTO to Post entity
            Post post = modelMapper.map(postDTO, Post.class);
            //Save the new Post to the database using PostRepository
            postRepository.save(post);
            //Check the new Post
            if(postRepository.findById(postDTO.getPostid()).isEmpty()){
                return false;
            }
        } catch (IllegalAccessError illegalAccessError) {
            isCreated = false;
        }
        return isCreated;
    }
}
