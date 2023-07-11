package com.foodrec.backend.TagAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodrec.backend.AccountAPI.entity.AccountTag;
import com.foodrec.backend.RecipeAPI.entity.RecipeTag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @Column(name = "tagid")
    private String tagId;

    @Column(name = "tagname")
    private String tagName;

    @JsonIgnore
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<AccountTag> accountTags;

    @JsonIgnore
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<RecipeTag> recipeTags;
}
