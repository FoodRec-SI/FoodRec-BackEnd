package com.foodrec.backend.PostAPI.command.update_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.UpdatePostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.exception.DuplicateExceptionHandler;
import com.foodrec.backend.exception.InvalidDataExceptionHandler;
import com.foodrec.backend.exception.NotFoundExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class UpdatePostCommandHandler implements Command.Handler<UpdatePostCommand, Boolean> {
    private final PostRepository postRepository;

    public UpdatePostCommandHandler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    @Override
    public Boolean handle(UpdatePostCommand command) {
        UpdatePostDTO updatePostDTO = command.getUpdatePostDTO();
        if (updatePostDTO.getStatus() == null || updatePostDTO.getPostId() == null ||
                updatePostDTO.getModeratorName() == null) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Optional<Post> optionalPost = postRepository.findById(updatePostDTO.getPostId());
        if (optionalPost.isEmpty()) {
            throw new NotFoundExceptionHandler("Post not found!");
        }
        if(optionalPost.get().getStatus() == updatePostDTO.getStatus().getValue()){
            throw new DuplicateExceptionHandler("Duplicate post status!");
        }
        Post post = optionalPost.get();
        post.setStatus(updatePostDTO.getStatus().getValue());
        post.setModeratorName(updatePostDTO.getModeratorName());
        postRepository.save(post);
        return true;
    }
}
