package com.foodrec.backend.PostAPI.command.delete_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.github.dockerjava.api.exception.UnauthorizedException;
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
        try{
            //Check if the postid is not exist
            Optional<Post> postOptional = postRepository.findById(command.getPostid());
            if(postOptional.isEmpty()){
                throw new IllegalArgumentException("Invalid postid");
            }
            //Check if the user is authorized to delete this post
            if(!postOptional.get().getUserid().equals(command.getUserid())){
                throw new UnauthorizedException("You are not authorized to delete this post!");
            }
            try {
                postRepository.deleteById(command.getPostid());
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
