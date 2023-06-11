package com.foodrec.backend.RecipeAPI.query.get_recipe_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.RecipeUtils;
import com.foodrec.backend.exception.InvalidDataExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GetRecipeByIdQueryHandler implements Command.Handler<GetRecipeByIdQuery, RecipeDTO> {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final RecipeUtils recipeUtils;

    public GetRecipeByIdQueryHandler(ModelMapper modelMapper,
                                     RecipeRepository recipeRepository,
                                     RecipeUtils recipeUtils) {
        this.modelMapper = modelMapper;
        this.recipeRepository = recipeRepository;
        this.recipeUtils = recipeUtils;
    }

    @Override
    public RecipeDTO handle(GetRecipeByIdQuery getRecipeByIdQuery)
            throws InvalidDataExceptionHandler {
        boolean isValidRecId = recipeUtils.validateRecipeId(getRecipeByIdQuery.getRecipeid());
        if (!isValidRecId)
            throw new InvalidDataExceptionHandler
                    ("Invalid Recipe Id detected. Please try again!");
        ArrayList<Recipe> recListTest = (ArrayList<Recipe>) recipeRepository.findAll();
        Recipe recipe = recipeRepository.findRecipeByRecipeId(getRecipeByIdQuery.getRecipeid());
        RecipeDTO recDto = null;
        if (recipe != null) {
            recDto = modelMapper.map(recipe, RecipeDTO.class);
        }
        return recDto;
    }
}
