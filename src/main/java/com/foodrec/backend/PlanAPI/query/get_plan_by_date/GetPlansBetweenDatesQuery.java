package com.foodrec.backend.PlanAPI.query.get_plan_by_date;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.dto.DateDTO;
import com.foodrec.backend.PlanAPI.dto.ReadBasicPlanDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlansBetweenDatesQuery implements Command<List<ReadBasicPlanDTO>> {
    private DateDTO dateDTO;
}
