package com.foodrec.backend.PostAPI.command.delete_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.DeletePostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.exception.InvalidDataExceptionHandler;
import com.foodrec.backend.exception.NotFoundExceptionHandler;
import com.foodrec.backend.exception.UnauthorizedExceptionHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeletePostCommandHandler implements Command.Handler<DeletePostCommand, Boolean> {

    private final PostRepository postRepository;

    public DeletePostCommandHandler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Boolean handle(DeletePostCommand command) {
        DeletePostDTO deletePostDTO = command.getDeletePostDTO();
        if (deletePostDTO.getPostId() == null || deletePostDTO.getUserName() == null) {
            throw new InvalidDataExceptionHandler("Invalid post!");
        }
        //Check if the postid is not exist
        Optional<Post> postOptional = postRepository.findById(deletePostDTO.getPostId());
        if (postOptional.isEmpty()) {
            throw new NotFoundExceptionHandler("Post cannot found!");
        }
        //Check if the user is authorized to delete this post
        if (!postOptional.get().getUserName().equals(deletePostDTO.getUserName())) {
            throw new UnauthorizedExceptionHandler("You are not authorized to delete this post!");
        }
        postRepository.deleteById(deletePostDTO.getPostId());
        return true;
    }
}
