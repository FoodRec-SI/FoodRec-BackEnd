package com.foodrec.backend.RecipeAPI.command.create_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.entity.RecipeTag;
import com.foodrec.backend.RecipeAPI.entity.RecipeTagId;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.RecipeAPI.repository.RecipeTagRepository;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import com.foodrec.backend.Utils.IdGenerator;
import com.foodrec.backend.Utils.ImageUtils;
import com.foodrec.backend.Utils.RecipeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Component
public class CreateRecipeCommandHandler implements Command.Handler<CreateRecipeCommand, String> {

    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final RecipeUtils recipeUtils;
    private final TagRepository tagRepository;
    private final RecipeTagRepository recipeTagRepository;

    private final ArrayList<String> nonNullFields = new ArrayList<>
            (Arrays.asList("recipeName", "description", "image"));
    private final ArrayList<String> nonNegativeFields = new ArrayList<>
            (Arrays.asList("calories", "duration"));

    public CreateRecipeCommandHandler(RecipeRepository recipeRepository,
                                      RecipeUtils recipeUtils,
                                      ModelMapper modelMapper,
                                      TagRepository tagRepository,
                                      RecipeTagRepository recipeTagRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeUtils = recipeUtils;
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
        this.recipeTagRepository = recipeTagRepository;
    }

    private String updateImage(String existingImage, MultipartFile image, String folder, String userId) {
        ImageUtils imageUtils = new ImageUtils();
        try {
            imageUtils.deleteImage(existingImage);
            return (String) imageUtils.upload(image, folder, userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public String handle(CreateRecipeCommand createRecipeCommand) {
        ImageUtils imageUtils = new ImageUtils();
        CreateRecipeDTO createRecipeDTO = createRecipeCommand.getCreateRecipeDTO();
        boolean isValid = recipeUtils.fieldValidator(createRecipeCommand.getCreateRecipeDTO(),
                nonNullFields, nonNegativeFields);
        if (!isValid) {
            throw new InvalidDataExceptionHandler("One of the recipe attributes " +
                    "(name, description, calories, duration, image)" +
                    " is invalid. Please try again.");
        }

        String recipeId = IdGenerator.generateNextId(Recipe.class, "recipeId");
        String imageUrl = (String) imageUtils.upload(createRecipeDTO.getImage(),
                "recipe", String.valueOf(UUID.randomUUID()));

        Recipe recipeEntity = modelMapper.map(createRecipeDTO, Recipe.class);
        recipeEntity.setRecipeId(recipeId);
        recipeEntity.setImage(imageUrl);
        recipeEntity.setPublicStatus(false);
        recipeEntity.setUserId(createRecipeCommand.getUserid());
        recipeEntity.setStatus(true);
        recipeEntity.setRecipeTags(new HashSet<>());
        Set<String> tagsIdSet = createRecipeDTO.getTagsIdSet();
        Set<Tag> tags = tagRepository.getTagsByTagIdIn(tagsIdSet);
        if (tags.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found tag !");
        }
        List<RecipeTag> recipeTags = new ArrayList<>();
        for (Tag tag : tags) {
            RecipeTag recipeTag = new RecipeTag();
            RecipeTagId recipeTagId = new RecipeTagId(recipeEntity.getRecipeId(), tag.getTagId());
            recipeTag.setRecipeTagId(recipeTagId);
            recipeTag.setRecipe(recipeEntity);
            recipeTag.setTag(tag);
            recipeTags.add(recipeTag);
        }
        recipeEntity.getRecipeTags().addAll(recipeTags);
        recipeRepository.save(recipeEntity);
        recipeTagRepository.saveAll(recipeTags);
        return recipeId;
    }
}
