package com.foodrec.backend.CollectionAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodrec.backend.PostAPI.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "collection")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    @Id
    @Column(name = "collectionid")
    private String collectionId;
    @Column(name = "collectionname")
    private String collectionName;
    @Column(name = "description")
    private String description;
    @Column(name = "userid")
    private String userId;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            },
            mappedBy = "collections")
    @JsonIgnore
    private Set<Post> posts = new HashSet<>();
}
