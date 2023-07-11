package com.foodrec.backend.RatingAPI.repository;

import com.foodrec.backend.LikeAPI.entity.Likes;
import com.foodrec.backend.RatingAPI.entity.Rating;
import com.foodrec.backend.RatingAPI.entity.RatingCompositeKey;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingCompositeKey> {
    Rating findRatingByAccount_UserIdAndPost_PostId(String userId,String postId);
    List<Rating> getRatingsByPost_PostId(String postId);
}
