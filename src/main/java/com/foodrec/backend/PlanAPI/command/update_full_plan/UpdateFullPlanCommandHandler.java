package com.foodrec.backend.PlanAPI.command.update_full_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.command.create_base_plan.CreateBasePlanCommand;
import com.foodrec.backend.PlanAPI.dto.CreateBasePlanDTO;
import com.foodrec.backend.PlanAPI.dto.BasePlanDTO;
import com.foodrec.backend.PlanAPI.dto.FullPlanDTO;
import com.foodrec.backend.PlanAPI.dto.UpdateFullPlanDTO;
import com.foodrec.backend.PlanAPI.entity.Plan;
import com.foodrec.backend.PlanAPI.repository.PlanRepository;
import com.foodrec.backend.Utils.IdGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


@Component
public class UpdateFullPlanCommandHandler
        implements Command.Handler<UpdateFullPlanCommand, FullPlanDTO> {

    private ModelMapper modelMapper;
    private PlanRepository planRepository;
    public UpdateFullPlanCommandHandler(ModelMapper modelMapper,
                                        PlanRepository planRepository){
        this.modelMapper = modelMapper;
        this.planRepository = planRepository;
    }
    private LocalDateTime getDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime;
    }
    @Override
    public FullPlanDTO handle(UpdateFullPlanCommand command) {
        return null;
    }
}
