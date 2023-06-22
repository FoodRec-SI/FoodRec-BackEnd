package com.foodrec.backend.TagAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.entity.RecipeTag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    @OneToMany(mappedBy = "tag")
    private Set<RecipeTag> recipeTags;
    @ManyToMany(mappedBy = "accountTags")
    @JsonManagedReference
    private Set<Account> accounts = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "tag")
    @JsonIgnore
    private Set<Recipe> recipes = new HashSet<>();
}
