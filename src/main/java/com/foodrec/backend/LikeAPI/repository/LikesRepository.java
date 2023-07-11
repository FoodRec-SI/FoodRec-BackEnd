package com.foodrec.backend.LikeAPI.repository;

import com.foodrec.backend.LikeAPI.entity.Likes;
import com.foodrec.backend.LikeAPI.entity.LikesCompositeKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, LikesCompositeKey> {
//    Page<Likes> getLikesByAccount_UserId(String userId, Pageable pageable);
}
