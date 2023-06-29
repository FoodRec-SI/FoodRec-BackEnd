package com.foodrec.backend.MealAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "meal")
    @JsonIgnore
    private Set<MealPost> mealPosts;
}
