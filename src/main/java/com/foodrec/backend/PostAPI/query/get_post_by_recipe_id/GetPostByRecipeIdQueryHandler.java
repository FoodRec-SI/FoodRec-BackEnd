package com.foodrec.backend.PostAPI.query.get_post_by_recipe_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetPostByRecipeIdQueryHandler implements Command.Handler<GetPostByRecipeIdQuery, Boolean> {
    private final RecipeRepository recipeRepository;

    public GetPostByRecipeIdQueryHandler(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public Boolean handle(GetPostByRecipeIdQuery query) {
        if (query.getRecipeId().isEmpty()) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        return recipeRepository.findById(query.getRecipeId()).get().isPublicStatus();
    }
}
