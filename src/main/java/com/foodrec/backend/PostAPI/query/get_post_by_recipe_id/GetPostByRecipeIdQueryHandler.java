package com.foodrec.backend.PostAPI.query.get_post_by_recipe_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class GetPostByRecipeIdQueryHandler implements Command.Handler<GetPostByRecipeIdQuery, Boolean> {
    private final PostRepository postRepository;

    public GetPostByRecipeIdQueryHandler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    @Override
    public Boolean handle(GetPostByRecipeIdQuery query) {
        if (query.getRecipeId().isEmpty()) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        return postRepository.existsByRecipeIdAndStatusIn(query.getRecipeId(), Arrays.asList(PostStatus.PENDING_APPROVAL.getValue(), PostStatus.APPROVED.getValue()));
    }
}
