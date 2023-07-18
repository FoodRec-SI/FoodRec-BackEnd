package com.foodrec.backend.PlanAPI.command.update_full_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.dto.ReadBasicPlanDTO;
import com.foodrec.backend.PlanAPI.dto.UpdateFullPlanDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFullPlanCommand implements Command<ReadBasicPlanDTO> {
    private String userId;
    private UpdateFullPlanDTO updateFullPlanDTO;
}
