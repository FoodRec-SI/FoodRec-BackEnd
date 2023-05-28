package com.foodrec.backend.RecipeAPI.service.Query;

import com.foodrec.backend.RecipeAPI.dto.RecipeDto;
import com.foodrec.backend.RecipeAPI.model.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeQueryService {
    List<RecipeDto> findAllRecipes();
    RecipeDto findRecipeByRecipeid(String recipeid);
}
