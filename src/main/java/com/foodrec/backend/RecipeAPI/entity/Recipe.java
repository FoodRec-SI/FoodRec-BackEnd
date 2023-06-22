package com.foodrec.backend.RecipeAPI.entity;

import com.foodrec.backend.TagAPI.entity.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Table(name = "recipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {
    @Id
    @Column(name = "recipeid")
    private String recipeId;

    @Column(name = "recipename")
    private String recipeName;

    @Column(name = "description")
    private String description;

    @Column(name = "calories")
    private int calories;

    @Column(name = "userid")
    private String userId;

    @Column(name = "duration")
    private int duration;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private boolean status;

    @Column(name = "instructions")
    private String instructions;

    @OneToMany(mappedBy = "recipe")
    private Set<RecipeTag> recipeTags;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "recipe_tag", joinColumns = @JoinColumn(name = "recipeid"), inverseJoinColumns = @JoinColumn(name = "tagid"))
    private Set<Tag> tags = new HashSet<>();
}