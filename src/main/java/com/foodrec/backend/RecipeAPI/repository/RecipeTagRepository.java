package com.foodrec.backend.RecipeAPI.repository;

import com.foodrec.backend.RecipeAPI.entity.RecipeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RecipeTagRepository extends JpaRepository<RecipeTag, String> {
    void deleteRecipeTagByRecipe_RecipeId(String recipeId);

    List<RecipeTag> getRecipeTagsByTag_TagId(String tagId);

    List<RecipeTag> getRecipeTagsByTag_TagIdIn(Set<String> tagId);
}
