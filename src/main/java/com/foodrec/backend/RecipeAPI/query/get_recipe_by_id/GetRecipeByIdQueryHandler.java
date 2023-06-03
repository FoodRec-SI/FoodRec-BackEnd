package com.foodrec.backend.RecipeAPI.query.get_recipe_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

//Cú pháp: Command.Handler<Command_Class,Kiểu_dl_trả về)
@Component
public class GetRecipeByIdQueryHandler implements Command.Handler<GetRecipeByIdQuery,RUDRecipeDTO> {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;

    public GetRecipeByIdQueryHandler(ModelMapper modelMapper, RecipeRepository recipeRepository) {
        this.modelMapper = modelMapper;
        this.recipeRepository = recipeRepository;
    }
    @Override
    public RUDRecipeDTO handle(GetRecipeByIdQuery getRecipeByIdQuery){
        Recipe recipe = recipeRepository.findRecipeByRecipeId(getRecipeByIdQuery.getRecipeid());
        RUDRecipeDTO recDto = null;

        if(recipe!=null){
            recDto = modelMapper.map(recipe, RUDRecipeDTO.class);
        }

        return recDto;
    }

}
