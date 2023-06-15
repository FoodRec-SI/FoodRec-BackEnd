package com.foodrec.backend.RecipeAPI.command.create_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;

import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.IdGenerator;
import com.foodrec.backend.Utils.RecipeUtils;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CreateRecipeCommandHandler implements Command.Handler<CreateRecipeCommand, RecipeDTO> {

    private final RecipeRepository recipeRepository;
    private ModelMapper modelMapper;
    private final RecipeUtils recipeUtils;
    private final ArrayList<String> nonNullFields = new ArrayList<>
            (Arrays.asList("recipeName", "description", "image"));
    private final ArrayList<String> nonNegativeFields = new ArrayList<>
            (Arrays.asList("calories", "duration"));

    public CreateRecipeCommandHandler(RecipeRepository recipeRepository,
                                      RecipeUtils recipeUtils,
                                      ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeUtils = recipeUtils;
        this.modelMapper = modelMapper;
    }

    public RecipeDTO handle(CreateRecipeCommand createRecipeCommand)
            throws InvalidDataExceptionHandler {
        RecipeDTO result = null;
        boolean isValid =
                recipeUtils.fieldValidator(createRecipeCommand.getCreateRecipeDTO(),
                        nonNullFields, nonNegativeFields);
        if (isValid == false) {
            throw new InvalidDataExceptionHandler("One of the recipe attributes " +
                    "(name,description, calories, duration, image)" +
                    " is invalid. Please try again.");
        }
        Recipe recEntity = modelMapper.map(createRecipeCommand.getCreateRecipeDTO(), Recipe.class);
        recEntity.setRecipeId(IdGenerator.generateNextId(Recipe.class,"recipeId"));
        recEntity.setUserId(createRecipeCommand.getUserid());
        recEntity.setStatus(true);
        recipeRepository.save(recEntity);
        Optional<Recipe> isAddedRec = recipeRepository.findById(recEntity.getRecipeId());
        if (isAddedRec.get() != null) {
            result = modelMapper.map(isAddedRec.get(), RecipeDTO.class);
        }
        return result;
    }
}
