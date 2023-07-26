package com.foodrec.backend.PostAPI.repository;

import com.foodrec.backend.CollectionAPI.entity.Collection;
import com.foodrec.backend.PostAPI.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Page<Post> findAllByStatus(int status, Pageable pageable);

    Page<Post> findAllByStatusIn(List<Integer> statuses, Pageable pageable);

    Optional<Post> findPostByPostIdAndStatus(String postId, int status);

    Page<Post> findPostsByRecipeIdInAndStatus(List<String> recipeIds, int status, Pageable pageable);

    Post findFirstByPostCollections_CollectionAndStatusOrderByRecipeNameAsc(Collection collection, int status);

    Page<Post> getPostsByPostCollectionsCollectionAndStatus(Collection collection, int status, Pageable pageable);

    List<Post> getPostsByRecipeIdInAndStatus(List<String> recipeIds, int status);

    Optional<Post> getPostByPostIdAndUserId(String postId, String userId);

    Boolean existsByRecipeIdAndStatusIn(String recipeId, List<Integer> status);

    Page<Post> getPostByAverageScoreGreaterThanEqualAndStatus(double averageScore, int status, Pageable pageable);

    Page<Post> getPostsByModeratorIdAndStatusIn(String moderatorId, List<Integer> status, Pageable pageable);

    Page<Post> getPostsByLikes_Account_UserId(String userId, Pageable pageable);

    List<Post> findAllByStatus(int status);

    /*Finds the list of Posts based on the join table(MealPosts).
    * In the join table, find a list of Posts belong to 1 Meal
    , and find that Meal by the MealId.*/
    List<Post> getPostsByMealPosts_Meal_MealId(String mealId);

    Optional<Post> getFirstByMealPosts_Meal_MealIdOrderByRecipeNameAsc(String mealId);
}
