package com.foodrec.backend.TagAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @Column(name = "tagid")
    private String tagId;
    @Column(name = "tagname")
    private String tagName;
    @ManyToMany(mappedBy = "tagAndAccountList")
    private List<Account> accounts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "tag")
    @JsonIgnore
    private Set<Recipe> recipes = new HashSet<>();

    public Tag() {
    }

    public Tag(String tagId, String tagName, List<Account> accounts, Set<Recipe> recipes) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.accounts = accounts;
        this.recipes = recipes;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}
