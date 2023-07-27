package com.foodrec.backend.PlanAPI.query.get_plan_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.MealAPI.dto.MealDTO;
import com.foodrec.backend.PlanAPI.dto.ReadFullPlanDTO;
import com.foodrec.backend.PlanAPI.entity.Plan;
import com.foodrec.backend.PlanAPI.repository.PlanRepository;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class GetPlanByIdQueryHandler implements Command.Handler<GetPlanByIdQuery,ReadFullPlanDTO> {
    private final PlanRepository planRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    public GetPlanByIdQueryHandler(ModelMapper modelMapper,
                                   PlanRepository planRepository,
                                   PostRepository postRepository){
        this.planRepository = planRepository;
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public ReadFullPlanDTO handle(GetPlanByIdQuery command) {
        Plan plan = planRepository.findById(command.getPlanId()).get();

        ReadFullPlanDTO result = modelMapper.map(plan,ReadFullPlanDTO.class);
        /*Since the Meal List for the specified Plan is already mapped, we don't have to do so.*/
        /*Retrieves the list of Post for each Meal in the result.*/
        Set<MealDTO> mealDTOSet = result.getMealSet();
        for(MealDTO eachMealDTO:mealDTOSet){
            String mealId = eachMealDTO.getMealId();
            List<Post> posts = postRepository.getPostsByMealPosts_Meal_MealId(mealId);
            List<PostDTO> tempPostDTOList = new ArrayList<PostDTO>();
            for(Post eachPost:posts){
                PostDTO eachPostDTO = modelMapper.map(eachPost,PostDTO.class);
                tempPostDTOList.add(eachPostDTO);
            }
            eachMealDTO.setPostDTOList(tempPostDTOList);
        }
        return result;
    }
}
