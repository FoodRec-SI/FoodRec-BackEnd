package com.foodrec.backend.PostAPI.command.update_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UpdatePostCommandHandler implements Command.Handler<UpdatePostCommand, Boolean> {
    private final PostRepository postRepository;

    public UpdatePostCommandHandler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Boolean handle(UpdatePostCommand command) {
        boolean isUpdated = true;
        try {
            Post post = postRepository.findById(command.getPostid())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid postid"));
            post.setModeratorid(command.getModeratorid());
            if(post.getStatus() == command.getStatus()){
                throw new IllegalArgumentException("Duplicate status");
            }
            post.setStatus(command.getStatus());
            if(post.getStatus() > 4 || post.getStatus() < 2){
                throw new IllegalArgumentException("Is not valid!!!");
            }
            postRepository.save(post);
        } catch (IllegalAccessError illegalAccessError) {
            isUpdated = false;
        }
        return isUpdated;
    }
}
