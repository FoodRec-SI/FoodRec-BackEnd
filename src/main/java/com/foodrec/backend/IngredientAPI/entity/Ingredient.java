package com.foodrec.backend.IngredientAPI.entity;

import com.foodrec.backend.ListAPI.Entity.Lists;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ingredient {
    @Id
    @Column(name="ingredientid")
    private String ingredientId;
    @Column(name="description")
    private String description;

    @OneToMany(mappedBy="ingredient")
    private Set<Lists> ingredientList;
}
