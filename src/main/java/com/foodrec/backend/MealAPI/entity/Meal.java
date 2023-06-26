package com.foodrec.backend.MealAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
