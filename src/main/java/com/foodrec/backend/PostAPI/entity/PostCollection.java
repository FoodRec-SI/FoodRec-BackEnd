package com.foodrec.backend.PostAPI.entity;

import com.foodrec.backend.CollectionAPI.entity.Collection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post_collection")
public class PostCollection {
    @EmbeddedId
    private PostCollectionId postCollectionId;
    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "postid", insertable = false, updatable = false)
    private Post post;
    @ManyToOne
    @MapsId("collectionId")
    @JoinColumn(name = "collectionid", insertable = false, updatable = false)
    private Collection collection;
}
