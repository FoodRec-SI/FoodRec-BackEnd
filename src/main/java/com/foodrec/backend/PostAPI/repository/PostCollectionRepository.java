package com.foodrec.backend.PostAPI.repository;

import com.foodrec.backend.CollectionAPI.entity.Collection;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCollectionRepository extends JpaRepository<PostCollection, String> {
    void deleteAllByCollection_CollectionId(String collectionId);

    void deleteByPostAndCollection(Post post, Collection collection);

    int countByCollection_CollectionId(String collectionId);
}
