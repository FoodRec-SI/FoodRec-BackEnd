package com.foodrec.backend.TagAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @Column(name = "tagid")
    private String tagId;
    @Column(name = "tagname")
    private String tagName;
    @ManyToMany(mappedBy = "accountTags")
    private List<Account> accounts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "tag")
    @JsonIgnore
    private Set<Recipe> recipes = new HashSet<>();
}
