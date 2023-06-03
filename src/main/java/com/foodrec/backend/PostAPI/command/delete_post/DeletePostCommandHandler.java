package com.foodrec.backend.PostAPI.command.delete_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.DeletePostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.exception.InvalidDataExceptionHandler;
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
        boolean isRemoved = true;
        DeletePostDTO deletePostDTO = command.getDeletePostDTO();
        try{
            //Check if the postid is not exist
            Optional<Post> postOptional = postRepository.findById(deletePostDTO.getPostid());
            if(postOptional.isEmpty()){
                throw new InvalidDataExceptionHandler("Invalid postid");
            }
            //Check if the user is authorized to delete this post
            if(!postOptional.get().getUserid().equals(deletePostDTO.getUserid())){
                throw new UnauthorizedExceptionHandler("You are not authorized to delete this post!");
            }
            try {
                postRepository.deleteById(deletePostDTO.getPostid());
            } catch (Exception e) {
                throw new RuntimeException("Could not delete post: " + e);
            }
        }
        catch(IllegalAccessError illegalAccessError){
            isRemoved = false;
        }
        return isRemoved;
    }
}
