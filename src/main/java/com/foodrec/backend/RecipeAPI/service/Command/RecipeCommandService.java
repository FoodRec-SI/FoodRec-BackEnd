package com.foodrec.backend.RecipeAPI.service.Command;

import com.foodrec.backend.RecipeAPI.dto.RecipeDto;
import com.foodrec.backend.RecipeAPI.model.Recipe;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface RecipeCommandService  {
    boolean insertRecipe(RecipeDto newRec);
    boolean deleteRecipeById(String id);
    boolean updateRecipeDetailsById(RecipeDto rec);
}
