package com.foodrec.backend.PlanAPI.command.remove_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.entity.Plan;
import com.foodrec.backend.PlanAPI.repository.PlanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;


@Component
public class RemovePlanCommandHandler implements Command.Handler<RemovePlanCommand,Boolean> {

    private ModelMapper modelMapper;
    private PlanRepository planRepository;
    public RemovePlanCommandHandler(ModelMapper modelMapper,
                                    PlanRepository planRepository){
        this.modelMapper = modelMapper;
        this.planRepository = planRepository;
    }

    @Override
    public Boolean handle(RemovePlanCommand command) {
        Boolean result = null;
        try{
            String planId = command.getRemovePlanDTO().getPlanId();
            planRepository.deleteById(planId);

            Optional<Plan> plan = planRepository.findById(planId);
            if(plan.get()!=null) result = false;
        }catch(NoSuchElementException e){
            result = true;
        }
        return result;
    }
}
