package com.foodrec.backend.PostAPI.query.get_posts_by_collection_id.get_plan_by_date;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.dto.ReadBasicPlanDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlansBetweenDatesQuery implements Command<List<ReadBasicPlanDTO>> {
    private Date startDate;
    private Date endDate;
}
