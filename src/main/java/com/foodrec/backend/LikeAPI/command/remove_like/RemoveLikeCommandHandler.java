package com.foodrec.backend.LikeAPI.command.remove_like;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.LikeAPI.entity.Likes;
import com.foodrec.backend.LikeAPI.entity.LikesCompositeKey;
import com.foodrec.backend.LikeAPI.repository.LikesRepository;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;


@Component
public class RemoveLikeCommandHandler implements Command.Handler<RemoveLikeCommand, Boolean> {
    @Autowired
    private LikesRepository likesRepository;

    public RemoveLikeCommandHandler(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    @Override
    public Boolean handle(RemoveLikeCommand command) {
        boolean result = false;
        try {
            String userId = command.getUserId();
            String postId = command.getPostId();

            LikesCompositeKey likesCompositeKey = new LikesCompositeKey(userId,postId);
            likesRepository.deleteById(likesCompositeKey);
            Optional<Likes> found = likesRepository.findById(likesCompositeKey);
            if (found.get() != null) result = false;
        } catch (NoSuchElementException e) {
            result = true;
        }
        return result;
    }
}
