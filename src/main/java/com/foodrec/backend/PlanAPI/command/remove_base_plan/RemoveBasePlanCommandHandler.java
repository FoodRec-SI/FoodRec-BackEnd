package com.foodrec.backend.PlanAPI.command.remove_base_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.command.create_base_plan.CreateBasePlanCommand;
import com.foodrec.backend.PlanAPI.dto.BasePlanDTO;
import com.foodrec.backend.PlanAPI.entity.Plan;
import com.foodrec.backend.PlanAPI.repository.PlanRepository;
import com.foodrec.backend.Utils.IdGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;


@Component
public class RemoveBasePlanCommandHandler implements Command.Handler<RemoveBasePlanCommand,Boolean> {

    private ModelMapper modelMapper;
    private PlanRepository planRepository;
    public RemoveBasePlanCommandHandler(ModelMapper modelMapper,
                                        PlanRepository planRepository){
        this.modelMapper = modelMapper;
        this.planRepository = planRepository;
    }

    @Override
    public Boolean handle(RemoveBasePlanCommand command) {
        Boolean result = null;
        try{
            String planId = command.getRemoveBasePlanDTO().getPlanId();
            planRepository.deleteById(planId);

            Optional<Plan> plan = planRepository.findById(planId);
            if(plan.get()!=null) result = false;
        }catch(NoSuchElementException e){
            result = true;
        }
        return result;
    }
}
