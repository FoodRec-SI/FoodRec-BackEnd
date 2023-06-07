package com.foodrec.backend.RecipeAPI.query.get_recipe_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.ReadRecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.exceptions.InvalidRecipeIdException;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.RecipeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

//Cú pháp: Command.Handler<Command_Class,Kiểu_dl_trả về)
@Component
public class GetRecipeByIdQueryHandler implements Command.Handler<GetRecipeByIdQuery, ReadRecipeDTO> {
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
    public ReadRecipeDTO handle(GetRecipeByIdQuery getRecipeByIdQuery)
            throws InvalidRecipeIdException {
        boolean isValidRecId =recipeUtils.validateRecipeId(getRecipeByIdQuery.getRecipeid());
        if(!isValidRecId)
            throw new InvalidRecipeIdException
                    ("Invalid Recipe Id detected. Please try again.");

        Recipe recipe = recipeRepository.findRecipeByRecipeId(getRecipeByIdQuery.getRecipeid());
        ReadRecipeDTO recDto = null;

        if (recipe != null) {
            recDto = modelMapper.map(recipe, ReadRecipeDTO.class);
        }

        return recDto;
    }

}
