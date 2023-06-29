package com.foodrec.backend.LikeAPI.command.remove_like;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.LikeAPI.entity.Likes;
import com.foodrec.backend.LikeAPI.entity.LikesCompositeKey;
import com.foodrec.backend.LikeAPI.repository.LikesRepository;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;


@Component
public class RemoveLikeCommandHandler implements Command.Handler<RemoveLikeCommand, Boolean> {
    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private PostRepository postRepository;

    public RemoveLikeCommandHandler(LikesRepository likesRepository,
                                    PostRepository postRepository) {
        this.likesRepository = likesRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Boolean handle(RemoveLikeCommand command) {
        boolean result = false;
        try {
            String userId = command.getUserId();
            String postId = command.getPostId();
            /*Step 0: Create a LikesCompositeKey to be deleted in the Database
            * using the LikesRepository*/
            LikesCompositeKey likesCompositeKey = new LikesCompositeKey(userId,postId);

            /*Step 1: Remove the Likes entity from the Join Table (Likes)*/
            likesRepository.deleteById(likesCompositeKey);
            Optional<Likes> found = likesRepository.findById(likesCompositeKey);
            if (found.get() != null) result = false;
        } catch (NoSuchElementException e) {
            result = true;
        }
        return result;
    }
}
