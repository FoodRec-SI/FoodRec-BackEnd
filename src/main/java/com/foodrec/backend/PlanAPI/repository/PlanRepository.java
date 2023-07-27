package com.foodrec.backend.PlanAPI.repository;

import com.foodrec.backend.PlanAPI.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface PlanRepository extends JpaRepository<Plan,String> {
    @Query(value="select * from plan where date>= ?1 and date <= ?2"
            ,nativeQuery = true)
    List<Plan> getPlansByDateBetween(Date startDate,Date endDate);
    Optional<Plan> getPlanByPlanId(String planId);
}
