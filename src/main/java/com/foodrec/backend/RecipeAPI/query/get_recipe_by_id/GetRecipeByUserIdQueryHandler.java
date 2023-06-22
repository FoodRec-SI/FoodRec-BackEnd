package com.foodrec.backend.RecipeAPI.query.get_recipe_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidPageInfoException;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import com.foodrec.backend.Utils.PageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetRecipeByUserIdQueryHandler implements Command.Handler<GetRecipeByUserIdQuery, Page<RecipeDTO>> {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final PageUtils pageUtils;
    private final TagRepository tagRepository;

    public GetRecipeByUserIdQueryHandler(ModelMapper modelMapper,
                                         RecipeRepository recipeRepository,
                                         PageUtils pageUtils,
                                         TagRepository tagRepository) {
        this.modelMapper = modelMapper;
        this.recipeRepository = recipeRepository;
        this.pageUtils = pageUtils;
        this.tagRepository = tagRepository;
    }

    @Override
    public Page<RecipeDTO> handle(GetRecipeByUserIdQuery command)
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
                Sort.by("recipeName").ascending());
        List<RecipeDTO> recipeDTOs = recipeRepository.findRecipesByUserIdAndStatus(command.getUserid(), true, pageable)
                .stream()
                .map((recipe) -> modelMapper.map(recipe, RecipeDTO.class))
                .collect(Collectors.toList());
        if (recipeDTOs.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found recipe !");
        }
        for (RecipeDTO eachRecipeDTO : recipeDTOs) {
            List<TagDTO> eachTagList = tagRepository.
                    findTagsByRecipesRecipeId(eachRecipeDTO.getRecipeId())
                    .stream().map((tag) -> modelMapper.map(tag, TagDTO.class))
                    .collect(Collectors.toList());
            eachRecipeDTO.setTags(eachTagList);
        }
        return new PageImpl<>(recipeDTOs, pageable, recipeDTOs.size());
    }
}
