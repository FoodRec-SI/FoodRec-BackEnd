package com.foodrec.backend.TagAPI.repository;

import com.foodrec.backend.TagAPI.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
}
