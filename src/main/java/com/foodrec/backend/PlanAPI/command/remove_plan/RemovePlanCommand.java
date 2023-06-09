package com.foodrec.backend.PlanAPI.command.remove_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.dto.RemovePlanDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemovePlanCommand implements Command<Boolean> {
    private RemovePlanDTO removePlanDTO;
}
