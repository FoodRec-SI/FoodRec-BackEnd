package com.foodrec.backend.PlanAPI.command.create_base_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.dto.CreateBasePlanDTO;
import com.foodrec.backend.PlanAPI.dto.BasePlanDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBasePlanCommand implements Command<BasePlanDTO> {
    private String userId;
    private CreateBasePlanDTO createBasePlanDTO;
}
