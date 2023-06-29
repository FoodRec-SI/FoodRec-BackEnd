package com.foodrec.backend.CollectionAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodrec.backend.PostAPI.entity.PostCollection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "collection")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<PostCollection> postCollections;
}
