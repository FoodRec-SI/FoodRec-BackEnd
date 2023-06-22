package com.foodrec.backend.RecipeAPI.entity;

import com.foodrec.backend.TagAPI.entity.Tag;
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
@Table(name = "recipe_tag")
public class RecipeTag {

    @EmbeddedId
    private RecipeTagId recipeTagId;

    @ManyToOne
    @MapsId("recipeid")
    @JoinColumn(name = "recipeid", insertable = false, updatable = false)
    private Recipe recipe;

    @ManyToOne
    @MapsId("tagid")
    @JoinColumn(name = "tagid", insertable = false, updatable = false)
    private Tag tag;
}
