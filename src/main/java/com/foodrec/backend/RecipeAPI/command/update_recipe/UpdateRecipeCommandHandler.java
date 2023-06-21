package com.foodrec.backend.RecipeAPI.command.update_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.RecipeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Component
public class UpdateRecipeCommandHandler implements Command.Handler<UpdateRecipeCommand, RecipeDTO> {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final RecipeUtils recipeUtils;
    private final ArrayList<String> nonNullFields = new ArrayList<>
            (Arrays.asList("recipeName", "description", "image"));
    private final ArrayList<String> nonNegativeFields = new ArrayList<>
            (Arrays.asList("calories", "duration"));

    public UpdateRecipeCommandHandler(RecipeRepository recipeRepository,
                                      RecipeUtils recipeUtils, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeUtils = recipeUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public RecipeDTO handle(UpdateRecipeCommand updateRecipeCommand)
            throws InvalidDataExceptionHandler {
        RecipeDTO recipeDTO = null;
        boolean isRecipeIdValid = recipeUtils.validateRecipeId(updateRecipeCommand
                .getUpdateRecipeDTO().getRecipeId());
        if (!isRecipeIdValid) {
            throw new InvalidDataExceptionHandler("The format of RecipeId is invalid." +
                    " Please try again.");
        }

        Optional<Recipe> foundRecipe = recipeRepository.findById(updateRecipeCommand.
                getUpdateRecipeDTO().getRecipeId());
        if (foundRecipe.isEmpty()) {
            throw new NotFoundExceptionHandler("The provided RecipeId " +
                    "is not found or already deleted. Please try again.");
        }
        if (!foundRecipe.get().isStatus()) {
            throw new NotFoundExceptionHandler("The provided RecipeId " +
                    "is not found or already deleted. Please try again.");
        }

        boolean isValid = recipeUtils.fieldValidator(updateRecipeCommand.
                getUpdateRecipeDTO(), nonNullFields, nonNegativeFields);
        if (!isValid) {
            throw new InvalidDataExceptionHandler
                    ("One of the recipe attributes (name,description, calories, duration, image)" +
                            " is invalid. Please try again.");
        }

        Recipe recEntity = modelMapper.map(updateRecipeCommand.getUpdateRecipeDTO(), Recipe.class);
        recEntity.setUserId("vathuglife");
        recEntity.setStatus(true);
        recipeRepository.save(recEntity);
        recipeDTO = modelMapper.map(recEntity, RecipeDTO.class);
        return recipeDTO;
    }
}