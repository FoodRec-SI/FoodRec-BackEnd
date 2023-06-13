package com.foodrec.backend.PostAPI.command.update_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.dto.UpdatePostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.Exception.DuplicateExceptionHandler;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class UpdatePostCommandHandler implements Command.Handler<UpdatePostCommand, PostDTO> {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public UpdatePostCommandHandler(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public PostDTO handle(UpdatePostCommand command) {
        UpdatePostDTO updatePostDTO = command.getUpdatePostDTO();
        if (updatePostDTO.getStatus() == null || updatePostDTO.getPostId() == null ||
                updatePostDTO.getModeratorName() == null || updatePostDTO.getStatus().equals(PostStatus.PENDING_APPROVAL)) {
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
        return modelMapper.map(post, PostDTO.class);
    }
}
