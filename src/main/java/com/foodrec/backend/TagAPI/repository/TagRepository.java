package com.foodrec.backend.TagAPI.repository;

import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    Set<Tag> findTagsByTagIdIn(Collection<String> tagIds);

    Set<Tag> getTagsByTagIdIn(Set<String> tagIds);

    Collection<Tag> getTagsByAccountTags_Account(Account account);

    List<Tag> findTagsByRecipeTags_Recipe(Recipe recipe);
    List<Tag> findTagsByRecipeTags_Recipe_RecipeId(String recipeId);
}
