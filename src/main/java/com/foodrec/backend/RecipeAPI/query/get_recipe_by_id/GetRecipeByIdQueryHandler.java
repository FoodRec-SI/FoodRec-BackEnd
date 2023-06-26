package com.foodrec.backend.RecipeAPI.query.get_recipe_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.RecipeAPI.repository.RecipeTagRepository;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GetRecipeByIdQueryHandler implements Command.Handler<GetRecipeByIdQuery, RecipeDTO> {
    private final RecipeRepository recipeRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public GetRecipeByIdQueryHandler(RecipeRepository recipeRepository, TagRepository tagRepository, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public RecipeDTO handle(GetRecipeByIdQuery query) {
        if (query.getRecipeId().isBlank() || query.getRecipeId() == null) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Optional<Recipe> optionalRecipe = recipeRepository.getRecipeByRecipeIdAndStatus(query.getRecipeId(), true);

        if (optionalRecipe.isEmpty()) {
            throw new NotFoundExceptionHandler("Recipe not found!");
        }
        if (!optionalRecipe.get().getUserId().equals(query.getUserid())) {
            throw new UnauthorizedExceptionHandler("You don't have permission to view this recipe !");
        }
        List<TagDTO> eachTagList = tagRepository.
                findTagsByRecipeTags_Recipe(recipeRepository.findById(query.getRecipeId()).get())
                .stream().map((tag) -> modelMapper.map(tag, TagDTO.class))
                .collect(Collectors.toList());
        RecipeDTO recipeDTO = modelMapper.map(optionalRecipe.get(), RecipeDTO.class);
        recipeDTO.setTags(eachTagList);
        return recipeDTO;
    }
}
