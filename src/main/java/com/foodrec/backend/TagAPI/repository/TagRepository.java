package com.foodrec.backend.TagAPI.repository;

import com.foodrec.backend.TagAPI.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    List<Tag> findTagsByRecipesRecipeId(String recipeId);
}
