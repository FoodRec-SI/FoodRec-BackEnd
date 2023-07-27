package com.foodrec.backend.PlanAPI.query.get_plan_by_date;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.dto.ReadBasicPlanDTO;
import com.foodrec.backend.PlanAPI.repository.PlanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class GetPlansByBetweenDatesQueryHandler implements Command.Handler<GetPlansBetweenDatesQuery,List<ReadBasicPlanDTO>> {
    private final PlanRepository planRepository;

    private final ModelMapper modelMapper;
    public GetPlansByBetweenDatesQueryHandler(ModelMapper modelMapper,
                                              PlanRepository planRepository){
        this.planRepository = planRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<ReadBasicPlanDTO> handle(GetPlansBetweenDatesQuery command) {

        List<ReadBasicPlanDTO> result
                = planRepository.getPlansByDateBetween(command.getStartDate(),
                command.getEndDate())
                .stream()
                .map((eachPlan)->modelMapper.map(eachPlan, ReadBasicPlanDTO.class)).
                toList();

        return result;
    }
}
