package com.foodrec.backend.RecipeAPI.repository;

import com.foodrec.backend.RecipeAPI.entity.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RecipeMongoRepository extends MongoRepository<Recipe, String> {
    Optional<Recipe> findByRecipeid(String recipeid);
}
