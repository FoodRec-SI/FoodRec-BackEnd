package com.foodrec.backend.RecipeAPI.repository.queries;

import com.foodrec.backend.RecipeAPI.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecipeCommandsRepository extends JpaRepository<Recipe,Long> {
    
}
