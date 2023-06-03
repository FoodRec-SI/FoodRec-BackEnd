package com.foodrec.backend.PostAPI.command.create_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.CreatePostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.Utils.IdGenerator;
import com.foodrec.backend.exception.DuplicateExceptionHandler;
import com.foodrec.backend.exception.InvalidDataExceptionHandler;
import com.foodrec.backend.exception.NotFoundExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CreatePostCommandHandler implements Command.Handler<CreatePostCommand, Boolean> {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final PostIdGenerator postIdGenerator;

    public CreatePostCommandHandler(PostRepository postRepository, ModelMapper modelMapper, PostIdGenerator postIdGenerator) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.postIdGenerator = postIdGenerator;
    }

    @Override
    public Boolean handle(CreatePostCommand command) {
        CreatePostDTO createPostDTO = command.getCreatePostDTO();
        boolean isCreated = true;
        try {
            if (createPostDTO.getRecipeid() == null || createPostDTO.getUserid() == null) {
                throw new InvalidDataExceptionHandler("Invalid Post!");
            }
            //Check duplicate Recipe
            List<Post> existingPost = postRepository.findAll();
            for (Post value : existingPost) {
                if (value.getRecipeid().equals(createPostDTO.getRecipeid())) {
                    throw new DuplicateExceptionHandler("Duplicate Recipe ID");
                }
            }
            //Convert PostDTO to Post entity
            Post post = modelMapper.map(createPostDTO, Post.class);
            //Add new data for Post entity
            String postId = IdGenerator.generateNextId(Post.class, "postid");
            post.setPostid(postId);
            LocalDateTime localDateTime = LocalDateTime.now();
            post.setTime(localDateTime);
            post.setStatus(1); // Set status is pending censorship

            //Save the new Post to the database using PostRepository
            postRepository.save(post);
            //Check the new Post
            if (postRepository.findById(postId).isEmpty()) {
                throw new NotFoundExceptionHandler("The post you just created cannot be found!");
            }
        } catch (InvalidDataExceptionHandler invalidDataExceptionHandler) {
            throw new InvalidDataExceptionHandler("Invalid Post Data!");
        }
        return isCreated;
    }
}
