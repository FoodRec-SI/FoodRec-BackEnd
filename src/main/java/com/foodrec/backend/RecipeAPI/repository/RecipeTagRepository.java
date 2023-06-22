package com.foodrec.backend.RecipeAPI.repository;

import com.foodrec.backend.RecipeAPI.entity.RecipeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeTagRepository extends JpaRepository<RecipeTag, String> {
    void deleteRecipeTagByRecipe_RecipeId(String recipeId);
}
