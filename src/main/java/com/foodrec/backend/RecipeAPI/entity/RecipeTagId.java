package com.foodrec.backend.RecipeAPI.entity;

import com.foodrec.backend.TagAPI.entity.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeTagId implements Serializable {
    @Column(name = "recipeid")
    private String recipeId;
    @Column(name = "tagid")
    private String tagId;

}
