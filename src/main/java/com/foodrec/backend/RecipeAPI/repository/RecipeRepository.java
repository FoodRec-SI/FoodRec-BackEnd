package com.foodrec.backend.RecipeAPI.repository;

import com.foodrec.backend.RecipeAPI.entity.Recipe;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,String> {
    Page<Recipe> findRecipesByUserIdAndStatus(String userid,boolean status,Pageable pageable);
    @Transactional
    @Modifying
    @Query(value="UPDATE recipe SET status = false WHERE recipeid = :recipeid",nativeQuery = true)
    void updateRecipeStatusById(@Param("recipeid") String recipeid);

    @Query(value="SELECT * FROM recipe WHERE status = true",nativeQuery = true)
    List<Recipe> findAllRecipes(Pageable pageable);
    @Query(value="SELECT * FROM Recipe WHERE recipeid = :recipeid AND status = true",nativeQuery = true)
    Recipe findRecipeByRecipeId(@Param("recipeid") String recipeid);
    List<Recipe> findRecipesByTagTagId(String tagId);
}
