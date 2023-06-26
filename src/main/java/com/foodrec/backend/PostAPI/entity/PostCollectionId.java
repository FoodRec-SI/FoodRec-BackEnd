package com.foodrec.backend.PostAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCollectionId implements Serializable {
    @Column(name = "postid")
    private String postId;
    @Column(name = "collectionid")
    private String collectionId;
}
