package com.foodrec.backend.TagAPI.entity;

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
    @ManyToMany(mappedBy = "tagAndRecipeList")
    private List<Recipe> recipes = new ArrayList<>();

    public Tag() {
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
}
