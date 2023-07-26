package com.foodrec.backend.MealAPI.repository;

import com.foodrec.backend.MealAPI.entity.MealPost;
import com.foodrec.backend.PostAPI.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealPostRepository extends JpaRepository<MealPost, String> {
    List<Post> findByMeal_MealId(String mealId);
    void deleteAllByMeal_MealId(String mealId);
}
