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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

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
        if (isRecipeIdValid == false) {
            throw new InvalidDataExceptionHandler("The format of RecipeId is invalid." +
                    " Please try again.");
        }

        Optional<Recipe> foundRecipe = recipeRepository.findById(updateRecipeCommand.
                getUpdateRecipeDTO().getRecipeId());
        if (foundRecipe.isEmpty()) {
            throw new NotFoundExceptionHandler("The provided RecipeId " +
                    "is not found or already deleted. Please try again.");
        }
        if (foundRecipe.get().isStatus() == false) {
            throw new NotFoundExceptionHandler("The provided RecipeId " +
                    "is not found or already deleted. Please try again.");
        }

        boolean isValid = recipeUtils.fieldValidator(updateRecipeCommand.
                getUpdateRecipeDTO(), nonNullFields, nonNegativeFields);
        if (isValid == false) {
            throw new InvalidDataExceptionHandler
                    ("One of the recipe attributes (name,description, calories, duration, image)" +
                            " is invalid. Please try again.");
        }

        //Step 1: Updates the recipe details
        Recipe recEntity = new Recipe(
                updateRecipeDTO.getRecipeId(),
                updateRecipeDTO.getRecipeName(),
                updateRecipeDTO.getDescription(),
                updateRecipeDTO.getCalories(),
                updateRecipeCommand.getUserId(),
                updateRecipeDTO.getDuration(),
                imageUrl,
                true,
                updateRecipeDTO.getInstructions()
        );
        recEntity.setUserId(updateRecipeCommand.getUserId());
        recEntity.setStatus(true);
        recipeRepository.save(recEntity);

        //Step 2: Updates the tag list of that recipe
        Recipe updatedRec = recipeRepository.findById(recEntity.getRecipeId()).get();
        Set<Tag> tags = updatedRec.getTags();
        for(String eachTagId:updateRecipeCommand.getUpdateRecipeDTO().getTagIdList()){
            Tag eachTag = tagRepository.findById(eachTagId).get();
            tags.add(eachTag); //adds the new Tags.
        }
        updatedRec.setTags(tags);
        recipeRepository.save(updatedRec);

        //Step 3: Returns the result of the updated recipe (in DTO).
        result = modelMapper.map(updatedRec, RecipeDTO.class);
        return result;
    }
}