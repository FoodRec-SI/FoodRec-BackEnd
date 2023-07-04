package com.foodrec.backend.MealAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodrec.backend.PlanAPI.entity.Plan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "meal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {

    @Id
    @Column(name = "mealid")
    private String mealId;

    @Column(name = "mealname")
    private String mealName;

    @Column(name = "userid")
    private String userid;

    @Column(name = "calories")
    private int calories;

    @Column(name = "planid")
    private String planId;

    @OneToMany(mappedBy = "meal")
    @JsonIgnore
    private Set<MealPost> mealPosts;

    @ManyToOne /*The relationship between Meal and Plan.
               1 Meal belongs to 1 Plan
                */
    @JoinColumn(name="planid") /*Name of the column in the Plan Table,
                                that this table (Meal) is joined to.*/
    private Plan plan;
}
