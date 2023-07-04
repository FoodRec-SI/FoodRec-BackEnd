package com.foodrec.backend.ListAPI.Entity;

import com.foodrec.backend.IngredientAPI.entity.Ingredient;
import com.foodrec.backend.PlanAPI.entity.Plan;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Lists {
    @Id
    private String listId;
    @Column(name="ingredient")
    private String ingredientId;
    @Column(name="planid")
    private String planId;
    @Column(name="description")
    private String description;
    @ManyToOne
    private Plan plan;
    @ManyToOne
    private Ingredient ingredient;
}
