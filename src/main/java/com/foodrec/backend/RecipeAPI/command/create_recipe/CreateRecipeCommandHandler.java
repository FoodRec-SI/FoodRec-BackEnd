package com.foodrec.backend.RecipeAPI.command.create_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;

import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
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
        List<String> tagIdList = createRecipeDTO.getTagIdList();

        /*Step 1: Adds the new Recipe Entity to the Recipe table*/
        Recipe recEntity = new Recipe(
                recipeId,
                createRecipeDTO.getRecipeName(),
                createRecipeDTO.getDescription(),
                createRecipeDTO.getCalories(),
                createRecipeCommand.getUserid(),
                createRecipeDTO.getDuration(),
                imageUrl,
                true,
                createRecipeDTO.getInstructions()
                );

        recipeRepository.save(recEntity);


        /*Step 2: Adds the RecipeId (from Step 1) and multiple TagIds (from createRecipeDTO)
        * to the Join Table (Recipe_Tag).*/

        //Gets the newly added recipe
        Recipe recipe = recipeRepository.
                findById(recEntity.getRecipeId()).get();
        Set<Tag> tags = recipe.getTags(); //Gets the tag list of that Recipe.

        /*Adds a List of Tags requested from the Front-end
         into "tags" of the newly added recipe.
        E.g. Bún Bò has a series of Tags (e.g. bữa sáng, món ăn Việt Nam)...*/
        for (String eachTagId:tagIdList){
            //Gets the FULL Tag Entity (including TagID (TAG000000),
            // TagName (bữa sáng() based on TagId (TAG000000)
            Tag tag = tagRepository.findById(eachTagId).get();
            tags.add(tag); //Adds the requested tag (from createRecipeDTO) to the Tags
        }
        recipe.setTags(tags);
        recipeRepository.save(recipe);

        //Step 3: Returns the full Recipe details, and the tags List.
        //To get the Recipe Data, you can CHOOSE 1 OUT OF 2 entities (Recipe/Tag).
        recipe = recipeRepository.
                findById(recEntity.getRecipeId()).get();
        //Maps the full recipe details
        result = modelMapper.map(recipe,RecipeDTO.class);
        //Gets the tags list of that recipe
        result.setTags(tagRepository.findTagsByRecipesRecipeId(recipeId));
        return result;
    }
}
