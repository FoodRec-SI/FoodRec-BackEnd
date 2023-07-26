package com.foodrec.backend.MealAPI.repository;

import com.foodrec.backend.MealAPI.entity.MealPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealPostRepository extends JpaRepository<MealPost, String> {
}
