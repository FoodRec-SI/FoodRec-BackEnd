package com.foodrec.backend.PlanAPI.repository;

import com.foodrec.backend.PlanAPI.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface PlanRepository extends JpaRepository<Plan,String> {
    
}
