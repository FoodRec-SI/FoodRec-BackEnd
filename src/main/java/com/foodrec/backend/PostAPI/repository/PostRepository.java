package com.foodrec.backend.PostAPI.repository;

import com.foodrec.backend.AccountAPI.entity.Account;
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

    List<Post> findPostByRecipeId(String recipeid);

    Page<Post> findAllByStatusIn(List<Integer> statuses, Pageable pageable);

    Optional<Post> findPostByPostIdAndStatus(String postId, int status);

    Page<Post> findPostsByRecipeNameContainingIgnoreCaseAndStatus(String recipeName, int status, Pageable pageable);

    Page<Post> findPostsByRecipeIdInAndStatus(List<String> recipeIds, int status, Pageable pageable);

    Post findFirstByPostCollections_CollectionAndStatusOrderByRecipeNameAsc(Collection collection, int status);

    Page<Post> getPostsByPostCollectionsCollectionAndStatus(Collection collection, int status, Pageable pageable);

    Page<Post> getPostsByRatingsAccountAndStatus(Account account, int status, Pageable pageable);
}
