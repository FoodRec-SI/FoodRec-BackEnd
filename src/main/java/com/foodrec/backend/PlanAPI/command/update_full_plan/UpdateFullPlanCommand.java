package com.foodrec.backend.PlanAPI.command.update_full_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.dto.CreateBasePlanDTO;
import com.foodrec.backend.PlanAPI.dto.BasePlanDTO;
import com.foodrec.backend.PlanAPI.dto.FullPlanDTO;
import com.foodrec.backend.PlanAPI.dto.UpdateFullPlanDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFullPlanCommand implements Command<FullPlanDTO> {
    private String userId;
    private UpdateFullPlanDTO updateFullPlanDTO;
}
