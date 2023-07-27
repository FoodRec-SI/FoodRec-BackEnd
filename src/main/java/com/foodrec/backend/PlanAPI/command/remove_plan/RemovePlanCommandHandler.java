package com.foodrec.backend.PlanAPI.command.remove_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.MealAPI.entity.Meal;
import com.foodrec.backend.MealAPI.entity.MealPost;
import com.foodrec.backend.MealAPI.repository.MealPostRepository;
import com.foodrec.backend.MealAPI.repository.MealRepository;
import com.foodrec.backend.PlanAPI.entity.Plan;
import com.foodrec.backend.PlanAPI.repository.PlanRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;


@Component
public class RemovePlanCommandHandler implements Command.Handler<RemovePlanCommand,Boolean> {

    private ModelMapper modelMapper;
    private PlanRepository planRepository;
    private MealRepository mealRepository;
    private MealPostRepository mealPostRepository;
    public RemovePlanCommandHandler(ModelMapper modelMapper,
                                    PlanRepository planRepository,
                                    MealRepository mealRepository,
                                    MealPostRepository mealPostRepository){
        this.modelMapper = modelMapper;
        this.planRepository = planRepository;
        this.mealRepository = mealRepository;
        this.mealPostRepository = mealPostRepository;
    }

    @Override
    @Transactional
    public Boolean handle(RemovePlanCommand command) {
        Boolean result = null;
        try{
            String planId = command.getRemovePlanDTO().getPlanId();
            /*Delete the Meals of a Plan, and Posts within each Plan first,
            to prevent the Foreign Key error.*/
            Optional<Plan> plan = planRepository.findById(planId);
            if(plan.get()!=null){
                Set<Meal> mealSet = plan.get().getMealSet();
                for(Meal eachMeal:mealSet){
                    Set<MealPost> tempMealPost = eachMeal.getMealPosts();
                    tempMealPost.clear();
                    eachMeal.setMealPosts(tempMealPost);
                    mealPostRepository.deleteAllByMeal_MealId(eachMeal.getMealId());
                }
                mealRepository.deleteAllByPlan_PlanId(planId);

                planRepository.deleteById(planId);
                result = true;
            }


        }catch(NoSuchElementException e){
            result = true;
        }
        return result;
    }
}
