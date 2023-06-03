package com.foodrec.backend.RecipeAPI.query.get_all;

import an.awesome.pipelinr.Command;


import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetAllRecipesQueryHandler implements Command.Handler<GetAllRecipesQuery, Page<RUDRecipeDTO>>{
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;

    public GetAllRecipesQueryHandler(ModelMapper modelMapper, RecipeRepository recipeRepository) {
        this.modelMapper = modelMapper;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Page<RUDRecipeDTO> handle(GetAllRecipesQuery command) {
        Pageable pageable = PageRequest.of(command.getPageNumber(), command.getPageSize(), Sort.by("recipeid").descending());
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);

        List<RUDRecipeDTO> recipeDTOs = recipePage.getContent().stream()
                .filter(recipe -> recipe.isHidden()==false)
                .map((recipe)-> modelMapper.map(recipe, RUDRecipeDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(recipeDTOs, pageable, recipePage.getTotalElements());
    }
}
