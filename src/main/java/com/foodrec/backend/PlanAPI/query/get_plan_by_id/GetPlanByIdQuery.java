package com.foodrec.backend.PlanAPI.query.get_plan_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.dto.ReadBasicPlanDTO;
import com.foodrec.backend.PlanAPI.dto.ReadFullPlanDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlanByIdQuery implements Command<ReadFullPlanDTO> {
    private String planId;
}
