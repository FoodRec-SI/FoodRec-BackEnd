package com.foodrec.backend.PlanAPI.entity;

import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.IngredientAPI.entity.Ingredient;
import com.foodrec.backend.ListAPI.Entity.Lists;
import com.foodrec.backend.MealAPI.entity.Meal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Plan {
    @Id
    @Column(name="planid")
    private String planId;
    @Column(name="planname")
    private String planName;
    @Column(name="userid")
    private String userId;
    @Column(name="description")
    private String description;

    /*Sidenote: This ingredient-list
    * is derived from ALL Meals within a Plan.*/
    @Column(name="ingredient-list")
    private String ingredientList;
    @Column(name="meal-quantity")
    private int mealQuantity;
    @Column(name="date")
    private LocalDateTime date;
    @Column(name="calories")
    private int calories;

    @OneToMany(mappedBy="plan")
    private Set<Meal> mealSet;

    @ManyToOne /*Many Plans are created by 1 Account ONLY.*/
    @JoinColumn(name="userid",updatable = false,insertable = false)
    //The column inside the Account table, which is used to map (1 Account - Many Plans)
    private Account account;


}
