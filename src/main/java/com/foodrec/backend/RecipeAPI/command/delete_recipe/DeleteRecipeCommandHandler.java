package com.foodrec.backend.RecipeAPI.command.delete_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.RecipeUtils;
import com.foodrec.backend.exception.InvalidDataExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteRecipeCommandHandler implements Command.Handler<DeleteRecipeCommand, Boolean> {
    @Autowired
    private final RecipeRepository recipeRepository;
    private final RecipeUtils recipeUtils;

    public DeleteRecipeCommandHandler(RecipeRepository recipeRepository,
                                      RecipeUtils recipeUtils) {
        this.recipeRepository = recipeRepository;
        this.recipeUtils = recipeUtils;
    }

    @Override
    public Boolean handle(DeleteRecipeCommand deleteRecipeCommand) throws InvalidDataExceptionHandler {
        boolean isValidRecId = recipeUtils.validateRecipeId(deleteRecipeCommand.getRecipeid());
        if (!isValidRecId)
            throw new InvalidDataExceptionHandler
                    ("Invalid Recipe Id detected. Please try again.");
        Optional<Recipe> foundRecipe = recipeRepository.findById(deleteRecipeCommand.getRecipeid());
        if (foundRecipe.isEmpty()) {
            return false;
        }
        if (foundRecipe.get().isStatus() == false) {
            return false;
        }
        recipeRepository.updateRecipeStatusById(deleteRecipeCommand.getRecipeid());
        return true;
    }
}
