package com.foodrec.backend.RecipeAPI.command.update_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.RecipeAPI.dto.UpdateRecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.entity.RecipeTag;
import com.foodrec.backend.RecipeAPI.entity.RecipeTagId;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.RecipeAPI.repository.RecipeTagRepository;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import com.foodrec.backend.Utils.ImageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UpdateRecipeCommandHandler implements Command.Handler<UpdateRecipeCommand, HttpStatus> {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;
    private final RecipeTagRepository recipeTagRepository;

    public UpdateRecipeCommandHandler(RecipeRepository recipeRepository,
                                      ModelMapper modelMapper,
                                      TagRepository tagRepository,
                                      RecipeTagRepository recipeTagRepository) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
        this.recipeTagRepository = recipeTagRepository;
    }

    @Transactional
    @Override
    public HttpStatus handle(UpdateRecipeCommand command) {
        ImageUtils imageUtils = new ImageUtils();
        UpdateRecipeDTO updateRecipeDTO = command.getUpdateRecipeDTO();
        if (updateRecipeDTO.getTagIdSet().isEmpty()) {
            throw new InvalidDataExceptionHandler("Invalid tag ID !");
        }
        Optional<Recipe> recipeOptional = recipeRepository.findById(updateRecipeDTO.getRecipeId());
        if (recipeOptional.isEmpty() || !recipeOptional.get().isStatus()) {
            throw new NotFoundExceptionHandler("The provided RecipeId " +
                    "is not found or already deleted. Please try again.");
        }
        Recipe recipe = recipeOptional.get();
        if (!command.getUserId().equals(recipe.getUserId())) {
            throw new UnauthorizedExceptionHandler("You are not authorized to update this recipe!");
        }
        String imageUrl = imageUtils.updateImage(recipe.getImage(), updateRecipeDTO.getImageFile(),
                "recipe", String.valueOf(UUID.randomUUID()));
        Set<String> tagIdSet = updateRecipeDTO.getTagIdSet();
        Set<Tag> tags = tagRepository.getTagsByTagIdIn(tagIdSet);
        recipeTagRepository.deleteRecipeTagByRecipe_RecipeId(recipe.getRecipeId());
        Recipe finalRecipe = recipe;
        List<RecipeTag> recipeTags = tags.stream()
                .map(tag -> new RecipeTag(new RecipeTagId(finalRecipe.getRecipeId(), tag.getTagId()), finalRecipe, tag))
                .collect(Collectors.toList());
        recipeTagRepository.saveAll(recipeTags);
        recipe = modelMapper.map(updateRecipeDTO, Recipe.class);
        recipe.setUserId(command.getUserId());
        recipe.setStatus(true);
        recipe.setImage(imageUrl);
        recipeRepository.save(recipe);
        return HttpStatus.OK;
    }
}

