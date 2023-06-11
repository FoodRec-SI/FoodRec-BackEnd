package com.foodrec.backend.RecipeAPI.query.get_all;

import an.awesome.pipelinr.Command;


import com.foodrec.backend.exception.InvalidPageInfoException;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.Utils.PageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetAllRecipesQueryHandler implements Command.Handler<GetAllRecipesQuery, Page<RecipeDTO>> {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final PageUtils pageUtils;

    public GetAllRecipesQueryHandler(ModelMapper modelMapper,
                                     RecipeRepository recipeRepository,
                                     PageUtils pageUtils) {
        this.modelMapper = modelMapper;
        this.recipeRepository = recipeRepository;
        this.pageUtils = pageUtils;
    }

    @Override
    public Page<RecipeDTO> handle(GetAllRecipesQuery command)
            throws InvalidPageInfoException {
        if (!pageUtils.isNumber(command.getPageNumber())
                || !pageUtils.isNumber(command.getPageSize())) {
            throw new InvalidPageInfoException("pageNumber or pageSize must be an Integer!");
        }
        int pageNumber = Integer.parseInt(command.getPageNumber());
        int pageSize = Integer.parseInt(command.getPageSize());
        if (pageNumber < 0 || pageSize < 0)
            throw new InvalidPageInfoException
                    ("pageNumber or pageSize can't be less than 0.");
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.by("recipeId").descending());
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);
        List<RecipeDTO> recipeDTOs = recipePage.getContent().stream()
                .filter(recipe -> recipe.isStatus() == true)
                .map((recipe) -> modelMapper.map(recipe, RecipeDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(recipeDTOs, pageable, recipePage.getTotalElements());
    }
}
