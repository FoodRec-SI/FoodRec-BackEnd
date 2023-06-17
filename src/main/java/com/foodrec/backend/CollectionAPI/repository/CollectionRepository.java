package com.foodrec.backend.CollectionAPI.repository;

import com.foodrec.backend.CollectionAPI.entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CollectionRepository extends JpaRepository<Collection, String> {
    Page<Collection> getCollectionsByUserId(String userId, Pageable pageable);
}
