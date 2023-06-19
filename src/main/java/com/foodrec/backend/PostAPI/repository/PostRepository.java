package com.foodrec.backend.PostAPI.repository;

import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.TagAPI.entity.Tag;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Page<Post> findAllByStatus(int status, Pageable pageable);

    List<Post> findPostByRecipeId(String recipeid);

    Page<Post> findAllByStatusIn(List<Integer> statuses, Pageable pageable);

    Optional<Post> findPostByPostIdAndStatus(String postId, int status);

    Page<Post> findPostsByRecipeNameContainingIgnoreCaseAndStatus(String recipeName, int status, Pageable pageable);

    //    @Query(value = "SELECT p.postid, p.userid, p.moderatorid, p.recipename, p.description, p.calories, p.duration, p.image, p.time " +
//            "FROM post p " +
//            "INNER JOIN p.recipe r ON p.recipeid = r.recipeid " +
//            "INNER JOIN r.tag t WHERE t.tagid IN :tagIds AND p.status = 2")
//    Page<Post> findPostsByTagIds(@Param("tagIds") List<String> tagIds);
    Page<Post> findPostsByRecipeIdInAndStatus(List<String> recipeIds, int status, Pageable pageable);
    Page<Post> findPostByAccountsUserIdAndStatus(String userId, int status, Pageable pageable);
}
