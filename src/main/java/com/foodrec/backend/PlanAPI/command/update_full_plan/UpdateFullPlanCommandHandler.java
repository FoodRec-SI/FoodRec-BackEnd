package com.foodrec.backend.PlanAPI.command.create_base_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PlanAPI.dto.BasePlanDTO;
import com.foodrec.backend.PlanAPI.dto.ReadBasePlanDTO;
import com.foodrec.backend.PlanAPI.entity.Plan;
import com.foodrec.backend.PlanAPI.repository.PlanRepository;
import com.foodrec.backend.Utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Component
public class CreateBasePlanCommandHandler implements Command.Handler<CreateBasePlanCommand, ReadBasePlanDTO> {

    private ModelMapper modelMapper;
    private PlanRepository planRepository;
    public CreateBasePlanCommandHandler(ModelMapper modelMapper,
                                        PlanRepository planRepository){
        this.modelMapper = modelMapper;
        this.planRepository = planRepository;
    }
    private LocalDateTime getDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime;
    }
    @Override
    public ReadBasePlanDTO handle(CreateBasePlanCommand command) {
        BasePlanDTO basePlanDTO = command.getBasePlanDTO();
        String userId = command.getUserId();
        String planId = IdGenerator.generateNextId(Plan.class,"planId");

        Plan newPlan = new Plan();
        modelMapper.map(basePlanDTO,newPlan);
        newPlan.setPlanId(planId);
        newPlan.setUserId(userId);
        newPlan.setDate(getDateTime());

        /*At this point, because the user hasn't GENERATED any Meals yet,
        * we have to generate dummy data for Ingredient List, Meal Quantity and Calories
        * to add to the database temporarily.*/

        newPlan.setIngredientList("null");
        newPlan.setMealQuantity(0);
        newPlan.setCalories(0);

        planRepository.save(newPlan);

        //Query the new plan with basic information from the db again to
        // make sure it is added to the database.
        Optional<Plan> optionalPlan = planRepository.findById(planId);
        if (optionalPlan.isEmpty()) return null;

        ReadBasePlanDTO result = modelMapper.map(optionalPlan.get(),ReadBasePlanDTO.class);
        return result;

    }
}
