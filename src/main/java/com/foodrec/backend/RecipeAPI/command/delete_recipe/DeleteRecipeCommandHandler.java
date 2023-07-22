package com.foodrec.backend.RecipeAPI.command.delete_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.RecipeAPI.repository.RecipeTagRepository;
import com.foodrec.backend.Utils.ImageUtils;
import com.foodrec.backend.Utils.RecipeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Component
public class DeleteRecipeCommandHandler implements Command.Handler<DeleteRecipeCommand, String> {
    private final RecipeRepository recipeRepository;
    private final RecipeTagRepository recipeTagRepository;
    private final RecipeUtils recipeUtils;

    public DeleteRecipeCommandHandler(RecipeRepository recipeRepository,
                                      RecipeTagRepository recipeTagRepository, RecipeUtils recipeUtils) {
        this.recipeRepository = recipeRepository;
        this.recipeTagRepository = recipeTagRepository;
        this.recipeUtils = recipeUtils;
    }

    @Transactional
    @Override
    public String handle(DeleteRecipeCommand deleteRecipeCommand) {
        boolean isValidRecId = recipeUtils.validateRecipeId(deleteRecipeCommand.getRecipeId());
        if (!isValidRecId)
            return "Invalid Recipe Id detected. Please try again!";
        Optional<Recipe> foundRecipe = recipeRepository.findById(deleteRecipeCommand.getRecipeId());
        if (foundRecipe.isEmpty() || !foundRecipe.get().isStatus()) {
            return "Recipe not found!";
        }
        if (!foundRecipe.get().getUserId().equals(deleteRecipeCommand.getUserId())) {
            return "You are not authorized to delete this recipe!";
        }
        Recipe recipe = foundRecipe.get();
        recipe.setStatus(false);
        ImageUtils imageUtils = new ImageUtils();
        try {
            imageUtils.deleteImage(foundRecipe.get().getImage());
            recipe.setImage(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        recipeTagRepository.deleteRecipeTagByRecipe_RecipeId(recipe.getRecipeId());
        recipeRepository.save(recipe);
        return "Successfully deleted recipe "+recipe.getRecipeId();
    }
}
