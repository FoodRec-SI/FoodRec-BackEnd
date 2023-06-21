package com.foodrec.backend.TagAPI.repository;

import com.foodrec.backend.TagAPI.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    List<Tag> findTagsByRecipesRecipeId(String recipeId);
    Set<Tag> getTagsByTagIdIn(Collection<String> tagIds);
    Collection<Tag> getTagsByAccountsUserId(String userId);
}
