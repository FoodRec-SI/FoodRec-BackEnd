package com.foodrec.backend.PlanAPI.command.remove_base_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.dto.BasePlanDTO;
import com.foodrec.backend.PlanAPI.dto.RemoveBasePlanDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveBasePlanCommand implements Command<Boolean> {
    private RemoveBasePlanDTO removeBasePlanDTO;
}
