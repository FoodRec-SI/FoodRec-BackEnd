package com.foodrec.backend.PostAPI.command.update_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.UpdatePostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.exception.DuplicateExceptionHandler;
import com.foodrec.backend.exception.InvalidDataExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UpdatePostCommandHandler implements Command.Handler<UpdatePostCommand, Boolean> {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    public UpdatePostCommandHandler(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public Boolean handle(UpdatePostCommand command) {
        boolean isUpdated = true;
        UpdatePostDTO updatePostDTO = command.getUpdatePostDTO();
        try {
            Post post = postRepository.findById(updatePostDTO.getPostid())
                    .orElseThrow(() -> new InvalidDataExceptionHandler("Invalid postid"));

            if(post.getStatus() == updatePostDTO.getStatus()){
                throw new DuplicateExceptionHandler("Duplicate post status");
            }
            if(post.getStatus() > 4 || post.getStatus() < 2){
                throw new InvalidDataExceptionHandler("Not valid post status!");
            }
            post = modelMapper.map(updatePostDTO, Post.class);
            postRepository.save(post);
        } catch (IllegalAccessError illegalAccessError) {
            isUpdated = false;
        }
        return isUpdated;
    }
}
