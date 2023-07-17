package com.foodrec.backend.PostAPI.repository;

import com.foodrec.backend.PostAPI.entity.PostELK;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface PostElasticsearchRepository extends ElasticsearchRepository<PostELK, String> {
    void deletePostELKByPostId(String postId);

    Optional<PostELK> getPostELKByPostId(String postId);

//    Page<PostELK> searchPostELKSByPostIdOrRecipeName(String postId, String recipeName, Pageable pageable);
//    @Query("{\"bool\": {\"should\": [{\"match\": {\"postId\": \"?0\"}}, {\"match\": {\"recipeName\": \"?0\"}}]}}")
@Query("{\"bool\": {\"should\": [{\"match\": {\"postId\": \"?0\"}}, {\"match\": {\"recipeName\": \"?1\"}}]}}")

    Page<PostELK> searchPostELKSByPostIdOrRecipeNameLike(String postId, String recipeName, Pageable pageable);

}
