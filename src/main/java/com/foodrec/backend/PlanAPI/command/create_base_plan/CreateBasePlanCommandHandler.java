package com.foodrec.backend.PlanAPI.command.create_base_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.PlanAPI.dto.CreateBasePlanDTO;
import com.foodrec.backend.PlanAPI.dto.BasePlanDTO;
import com.foodrec.backend.PlanAPI.entity.Plan;
import com.foodrec.backend.PlanAPI.repository.PlanRepository;
import com.foodrec.backend.Utils.IdGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;


@Component
public class CreateBasePlanCommandHandler implements Command.Handler<CreateBasePlanCommand, BasePlanDTO> {

    private ModelMapper modelMapper;
    private PlanRepository planRepository;
    public CreateBasePlanCommandHandler(ModelMapper modelMapper,
                                        PlanRepository planRepository){
        this.modelMapper = modelMapper;
        this.planRepository = planRepository;
    }
    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
    @Override
    public BasePlanDTO handle(CreateBasePlanCommand command) {
        CreateBasePlanDTO createBasePlanDTO = command.getCreateBasePlanDTO();
        String userId = command.getUserId();
        String planId = IdGenerator.generateNextId(Plan.class,"planId");

        /*Check if the given date belongs to an existing Plan.
        * If not, we won't allow the user to create a plan,
        * Since our goal is to ONLY ALLOW 1 PLAN PER DATE.*/
        LocalDateTime givenDateTime = convertToLocalDateTimeViaInstant(createBasePlanDTO.getPlanDate());
        String queryDateTime = givenDateTime.toString().split("T")[0];
        Optional<Plan> existingPlan = planRepository.findAll().stream()
                .filter(eachPlan->eachPlan.getDate()
                        .toString().contains(queryDateTime))
                        .findFirst();

        if(existingPlan.isPresent())
            throw new InvalidDataExceptionHandler("A plan with a similar date is found!");

        Plan newPlan = new Plan();
        modelMapper.map(createBasePlanDTO,newPlan);
        newPlan.setPlanId(planId);
        newPlan.setUserId(userId);
        newPlan.setDate(givenDateTime);

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

        BasePlanDTO result = modelMapper.map(optionalPlan.get(), BasePlanDTO.class);
        return result;

    }
}
