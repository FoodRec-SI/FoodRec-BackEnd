package com.foodrec.backend.RecipeAPI.command.create_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;

import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import com.foodrec.backend.Utils.IdGenerator;
import com.foodrec.backend.Utils.ImageUtils;
import com.foodrec.backend.Utils.RecipeUtils;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CreateRecipeCommandHandler implements Command.Handler<CreateRecipeCommand, RecipeDTO> {

    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final RecipeUtils recipeUtils;
    private final TagRepository tagRepository;

    private final ArrayList<String> nonNullFields = new ArrayList<>
            (Arrays.asList("recipeName", "description", "image"));
    private final ArrayList<String> nonNegativeFields = new ArrayList<>
            (Arrays.asList("calories", "duration"));

    public CreateRecipeCommandHandler(RecipeRepository recipeRepository,
                                      RecipeUtils recipeUtils,
                                      ModelMapper modelMapper,
                                      TagRepository tagRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeUtils = recipeUtils;
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;

    }

    private String updateImage(String existingImage, MultipartFile image, String folder, String userId) {
        ImageUtils imageUtils = new ImageUtils();
        try {
            imageUtils.delete(existingImage);
            return (String) imageUtils.upload(image, folder, userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RecipeDTO handle(CreateRecipeCommand createRecipeCommand)
            throws InvalidDataExceptionHandler {
        RecipeDTO result = null;
        ImageUtils imageUtils = new ImageUtils();
        CreateRecipeDTO createRecipeDTO = createRecipeCommand.getCreateRecipeDTO();
        boolean isValid =
                recipeUtils.fieldValidator(createRecipeCommand.getCreateRecipeDTO(),
                        nonNullFields, nonNegativeFields);
        if (!isValid) {
            throw new InvalidDataExceptionHandler("One of the recipe attributes " +
                    "(name,description, calories, duration, image)" +
                    " is invalid. Please try again.");
        }

        String recipeId = IdGenerator.generateNextId(Recipe.class, "recipeId");
        String imageUrl = (String) imageUtils.upload(createRecipeDTO.getImage(), "recipe", recipeId);
        List<String> tagsIdList = createRecipeDTO.getTagsIdList();

        /*Step 1: Adds the new Recipe Entity to the Recipe table*/
        Recipe recEntity = new Recipe();
        recEntity = modelMapper.map(createRecipeDTO, Recipe.class);
        recEntity.setImage(imageUrl);
        recEntity.setRecipeId(recipeId);
        recEntity.setUserId(createRecipeCommand.getUserid());
        recEntity.setStatus(true);

        /*Step 2: Adds the RecipeId (from Step 1) and multiple TagIds (from createRecipeDTO)
         * to the Join Table (Recipe_Tag).*/

        Set<Tag> tempTags = recEntity.getTags();
        tempTags.clear();//Gets the tag list of that Recipe.

        /*Adds a List of Tags requested from the Front-end
         into "tags" of the newly added recipe.
        E.g. Bún Bò has a series of Tags (e.g. bữa sáng, món ăn Việt Nam)...*/
        for (String eachTagId : tagsIdList) {
            //Gets the FULL Tag Entity (including TagID (TAG000000),
            // TagName (bữa sáng() based on TagId (TAG000000)
            Tag tag = tagRepository.findById(eachTagId).get();
            tempTags.add(tag); //Adds the requested tag (from createRecipeDTO) to the Tags
        }
        recEntity.setTags(tempTags);
        recipeRepository.save(recEntity);

        //Step 3: Returns the full Recipe details, and the tags List.
        //To get the Recipe Data, you can CHOOSE 1 OUT OF 2 entities (Recipe/Tag).
        recEntity = recipeRepository.
                findById(recEntity.getRecipeId()).get();


        //Gets the full details of the Added recipe, and its tags in the Dto form.
        result = modelMapper.map(recEntity, RecipeDTO.class);
        result.setTags(tagRepository.
                findTagsByRecipesRecipeId(recEntity.getRecipeId())
                .stream().map(tag -> modelMapper.map(tag,TagDTO.class))
                .collect(Collectors.toList()));
        return result;
    }
}
