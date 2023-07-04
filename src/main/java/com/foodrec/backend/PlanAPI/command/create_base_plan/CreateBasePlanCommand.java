package com.foodrec.backend.PlanAPI.command.create_base_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.dto.BasePlanDTO;
import com.foodrec.backend.PlanAPI.dto.ReadBasePlanDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBasePlanCommand implements Command<ReadBasePlanDTO> {
    private String userId;
    private BasePlanDTO basePlanDTO;
}
