package com.foodrec.backend.LikeAPI.repository;

import com.foodrec.backend.LikeAPI.entity.Likes;
import com.foodrec.backend.LikeAPI.entity.LikesCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, LikesCompositeKey> {
}
