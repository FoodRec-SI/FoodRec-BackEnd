package com.foodrec.backend.PlanAPI.query.get_plan_by_date;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.MealAPI.entity.Meal;
import com.foodrec.backend.MealAPI.repository.MealRepository;
import com.foodrec.backend.PlanAPI.dto.DateDTO;
import com.foodrec.backend.PlanAPI.dto.ReadBasicPlanDTO;
import com.foodrec.backend.PlanAPI.repository.PlanRepository;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class GetPlansByBetweenDatesQueryHandler implements Command.Handler<GetPlansBetweenDatesQuery, List<ReadBasicPlanDTO>> {
    private final PlanRepository planRepository;
    private final MealRepository mealRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public GetPlansByBetweenDatesQueryHandler(ModelMapper modelMapper, PlanRepository planRepository,
                                              MealRepository mealRepository, PostRepository postRepository) {
        this.planRepository = planRepository;
        this.modelMapper = modelMapper;
        this.mealRepository = mealRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public List<ReadBasicPlanDTO> handle(GetPlansBetweenDatesQuery command) {
        DateDTO dateDTO = command.getDateDTO();
        return planRepository.getPlansByDateBetween(dateDTO.getStartDate(), dateDTO.getEndDate()).stream().map(eachPlan -> {
            ReadBasicPlanDTO readBasicPlanDTO = modelMapper.map(eachPlan, ReadBasicPlanDTO.class);
            Optional<Meal> optionalMeal = mealRepository.getFirstByPlanIdOrderByMealNameAsc(eachPlan.getPlanId());
            Optional<Post> optionalPost = postRepository.getFirstByMealPosts_Meal_MealIdOrderByRecipeNameAsc(optionalMeal.get().getMealId());
            readBasicPlanDTO.setImage(optionalPost.get().getImage());
            return readBasicPlanDTO;
        }).toList();
    }
}
