package com.foodrec.backend.PostAPI.repository;

import com.foodrec.backend.PostAPI.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Page<Post> findAllByStatus(int status, Pageable pageable);
    List<Post> findPostByRecipeId(String recipeid);
    Page<Post> findAllByStatusIn(List<Integer> statuses, Pageable pageable);
}
