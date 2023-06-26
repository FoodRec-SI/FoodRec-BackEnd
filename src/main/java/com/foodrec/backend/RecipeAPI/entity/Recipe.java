package com.foodrec.backend.RecipeAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<RecipeTag> recipeTags;
}