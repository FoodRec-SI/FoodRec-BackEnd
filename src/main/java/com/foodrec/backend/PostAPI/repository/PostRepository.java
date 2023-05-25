package com.foodrec.backend.PostAPI.repository;

import com.foodrec.backend.PostAPI.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
}
