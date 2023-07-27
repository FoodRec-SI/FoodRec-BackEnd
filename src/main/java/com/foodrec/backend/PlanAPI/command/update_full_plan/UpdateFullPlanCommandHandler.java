package com.foodrec.backend.PlanAPI.command.update_full_plan;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.MealAPI.dto.CreateMealPerPlanDTO;
import com.foodrec.backend.MealAPI.entity.Meal;
import com.foodrec.backend.MealAPI.entity.MealPost;
import com.foodrec.backend.MealAPI.entity.MealPostId;
import com.foodrec.backend.MealAPI.repository.MealPostRepository;
import com.foodrec.backend.MealAPI.repository.MealRepository;
import com.foodrec.backend.PlanAPI.dto.ReadBasicPlanDTO;
import com.foodrec.backend.PlanAPI.dto.UpdateFullPlanDTO;
import com.foodrec.backend.PlanAPI.entity.Plan;
import com.foodrec.backend.PlanAPI.repository.PlanRepository;
import com.foodrec.backend.PostAPI.dto.CreatePostPerMealDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class UpdateFullPlanCommandHandler implements Command.Handler<UpdateFullPlanCommand, ReadBasicPlanDTO> {

    private ModelMapper modelMapper;
    private PlanRepository planRepository;
    private MealRepository mealRepository;
    private List<CreateMealPerPlanDTO> createMealPerPlanDTOList;
    private PostRepository postRepository;
    private MealPostRepository mealPostRepository;
    private AccountRepository accountRepository;

    public UpdateFullPlanCommandHandler(ModelMapper modelMapper,
                                        PlanRepository planRepository,
                                        MealRepository mealRepository,
                                        PostRepository postRepository,
                                        MealPostRepository mealPostRepository,
                                        AccountRepository accountRepository) {
        this.modelMapper = modelMapper;
        this.mealRepository = mealRepository;
        this.planRepository = planRepository;
        this.postRepository = postRepository;
        this.mealPostRepository = mealPostRepository;
        this.accountRepository = accountRepository;
    }


    /*Saves the 1 Meal - Many Posts relationship in the MealPost join table*/
    /*This includes:
     - Step 1: Adding new MealPost to mealPost list (in the Meal Entity)
     - Step 2: The MealPost join table */

    private Meal saveMealPostPerMealEntity(Meal eachMeal, CreateMealPerPlanDTO createMealPerPlanDTO,
                                           String mealId) {
        //Step 2: Save each MealPost (Post) Info of a Meal to the list.
        Set<MealPost> mealPosts = eachMeal.getMealPosts();
        Set<MealPost> tempMealPosts = null;
        if(mealPosts==null)
            tempMealPosts = new HashSet<>();
        else tempMealPosts = eachMeal.getMealPosts();

        List<CreatePostPerMealDTO> postList = createMealPerPlanDTO.getPostList();
        //The loop that updates the Posts list (mealPosts) of 1 Meal.
        //Removes all of the Previous Posts in that existing Meal.
        mealPostRepository.deleteAllByMeal_MealId(mealId);
        for (CreatePostPerMealDTO eachPost : postList) {
            String postId = eachPost.getPostId();
            MealPostId eachMealPostId = new MealPostId(mealId, postId);
            Post post = postRepository.findById(postId).get(); //Many Posts (for loop)
            Meal meal = mealRepository.findById(mealId).get(); //1 Current Meal

            MealPost mealPost = new MealPost(eachMealPostId, post, meal);
            tempMealPosts.add(mealPost);
            //Step 3: Saves each MealPost entity to the MealPost join table.
            mealPostRepository.save(mealPost);
        }
        eachMeal.setMealPosts(tempMealPosts);
        return eachMeal;
    }

    /*Updates all details within the Meal table*/
    private Plan saveMealEntity(UpdateFullPlanCommand command,
                                   Plan plan) {
        UpdateFullPlanDTO updateFullPlanDTO = command.getUpdateFullPlanDTO();
        String userId = command.getUserId();
        String planId = updateFullPlanDTO.getPlanId();
        createMealPerPlanDTOList = updateFullPlanDTO.getMealPerPlanList();
        Set<Meal> tempMealSet = plan.getMealSet();

        for (CreateMealPerPlanDTO createMealPerPlanDTO : createMealPerPlanDTOList) {
            String mealId = createMealPerPlanDTO.getMealId();
            Meal eachMeal = new Meal();
            eachMeal.setMealId(mealId);
            eachMeal.setMealName(createMealPerPlanDTO.getMealName());
            eachMeal.setUserid(userId);
            eachMeal.setCalories(createMealPerPlanDTO.getCurrentCalories());
            eachMeal.setPlanId(planId);
            eachMeal.setPlan(plan);
            mealRepository.save(eachMeal); /*First save in order to generate the
                                            Id of that Meal in database*/

            //Step 2: Updates the MealPost list of a meal.
            eachMeal = saveMealPostPerMealEntity
                    (eachMeal, createMealPerPlanDTO,mealId);
            tempMealSet.add(eachMeal);
            mealRepository.save(eachMeal);

        }
        plan.setMealSet(tempMealSet); //Updates the meal set of the given plan
        return plan;
    }


    private HashMap<String,Object> getTotalIngredientsAndCalories(){
        String finalIngredientList = "";
        int totalCalories = 0;
        HashMap<String,Object> result = new HashMap<>();
        for(CreateMealPerPlanDTO createMealPerPlanDTO : createMealPerPlanDTOList){
            List<CreatePostPerMealDTO> createPostPerMealDTOList
                                    = createMealPerPlanDTO.getPostList();
            for(CreatePostPerMealDTO post: createPostPerMealDTOList){
                /*Extracts the ingredient and calories details of each existing Post.
                Based on its Id.*/
                Post existingPost = postRepository.findById(post.getPostId()).get();
                String eachIngredient = existingPost.getIngredientList();
                int eachCalories = existingPost.getCalories();
                finalIngredientList += eachIngredient+"\n\n";
                totalCalories += eachCalories;
            }
        }
        result.put("ingredients-list",finalIngredientList);
        result.put("total-calories",totalCalories);
        return result;
    }
    /*Updates the remaining details of an existing Plan (ingredient-list, meal-quantity, calories,
      mealSet, account
    * */
    private boolean finalizePlanDetails(Plan plan) {

        String planId = plan.getPlanId();
        String userId = plan.getUserId();
        HashMap<String,Object> additionalDetails =
                getTotalIngredientsAndCalories();

        Account currentAccount = accountRepository.findById(userId).get();
        Optional<Plan> existingPlan = planRepository.findById(planId);
        if (existingPlan.isEmpty()) return false;


        Plan currentPlan = existingPlan.get();
        currentPlan.setAccount(currentAccount);
        currentPlan.setMealQuantity(createMealPerPlanDTOList.size());
        currentPlan.setIngredientList(additionalDetails.
                get("ingredients-list").toString());
        currentPlan.setCalories(Integer.parseInt
                        (additionalDetails.get("total-calories").toString()));
        planRepository.save(currentPlan);
        return true;
    }


    private ReadBasicPlanDTO returnFullPlanDTO(String planId){
        Plan plan = planRepository.findById(planId).get();
        ReadBasicPlanDTO result = modelMapper.map(plan, ReadBasicPlanDTO.class);
        return result;
    }


    @Override
    @Transactional
    public ReadBasicPlanDTO handle(UpdateFullPlanCommand command) {
        //The reason why we update Meal  BEFORE Plan is
        //that, we can get the full list of mealPerPlanDTO.
        String planId = command.getUpdateFullPlanDTO().getPlanId();
        Plan currentPlan = planRepository.findById(planId).get();


        /*tempPlan is already added with a list of Meals.
         Then, it is used to update (overwrite) the currentPlan.*/
        Plan tempPlan = saveMealEntity(command,currentPlan);
        if(tempPlan==null) return null;
        currentPlan = tempPlan;

        boolean isPlanUpdated = finalizePlanDetails(currentPlan);
        if(!isPlanUpdated) return null;

        ReadBasicPlanDTO readBasicPlanDTO = returnFullPlanDTO(planId);
        if(readBasicPlanDTO ==null) return null;
        return readBasicPlanDTO;
    }
}
