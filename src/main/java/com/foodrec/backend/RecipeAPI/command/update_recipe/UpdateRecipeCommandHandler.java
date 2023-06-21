package com.foodrec.backend.RecipeAPI.command.update_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.UpdateRecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import com.foodrec.backend.Utils.ImageUtils;
import com.foodrec.backend.Utils.RecipeUtils;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UpdateRecipeCommandHandler implements Command.Handler<UpdateRecipeCommand, RecipeDTO> {
    private final RecipeRepository recipeRepository;
    private ModelMapper modelMapper;
    private final TagRepository tagRepository;
    private final RecipeUtils recipeUtils;
    private final ArrayList<String> nonNullFields = new ArrayList<>
            (Arrays.asList("recipeName", "description", "image"));
    private final ArrayList<String> nonNegativeFields = new ArrayList<>
            (Arrays.asList("calories", "duration"));

    public UpdateRecipeCommandHandler(RecipeRepository recipeRepository,
                                      RecipeUtils recipeUtils, ModelMapper modelMapper,
                                      TagRepository tagRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeUtils = recipeUtils;
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
    }

    @Override
    public RecipeDTO handle(UpdateRecipeCommand updateRecipeCommand)
            throws InvalidDataExceptionHandler {
        RecipeDTO result = null;
        ImageUtils imageUtils = new ImageUtils();
        UpdateRecipeDTO updateRecipeDTO = updateRecipeCommand.getUpdateRecipeDTO();
        String recipeId = updateRecipeDTO.getRecipeId();
        String imageUrl = (String) imageUtils.upload(updateRecipeDTO.getImage(), "recipe", recipeId);


        boolean isRecipeIdValid = recipeUtils.validateRecipeId(updateRecipeCommand
                .getUpdateRecipeDTO().getRecipeId());
        if (!isRecipeIdValid) {
            throw new InvalidDataExceptionHandler("The format of RecipeId is invalid." +
                    " Please try again.");
        }

        Recipe foundRecipe = recipeRepository.findById(updateRecipeCommand.
                getUpdateRecipeDTO().getRecipeId()).get();
        if (foundRecipe==null) {
            throw new NotFoundExceptionHandler("The provided RecipeId " +
                    "is not found or already deleted. Please try again.");
        }
        if (!foundRecipe.isStatus()) {
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

//        Step 1: Updates the recipe details
        foundRecipe = modelMapper.map(updateRecipeDTO, Recipe.class);
        foundRecipe.setImage(imageUrl);
        foundRecipe.setRecipeId(recipeId);
        foundRecipe.setUserId(updateRecipeCommand.getUserId());
        foundRecipe.setStatus(true);

        //Step 2: Updates the tag list of that recipe
        List<String> tagIdList = updateRecipeCommand.getUpdateRecipeDTO().getTagsIdList();
        Set<Tag> tempTags = foundRecipe.getTags();
        tempTags.clear();

        for(String eachTagId:tagIdList){
            Tag eachTag = tagRepository.findById(eachTagId).get();
            tempTags.add(eachTag); //adds the new Tags.
        }
        foundRecipe.setTags(tempTags);
        recipeRepository.save(foundRecipe);

        //Step 3: Returns the result of the updated recipe (in DTO).
        result = modelMapper.map(foundRecipe, RecipeDTO.class);
        return result;
    }
}