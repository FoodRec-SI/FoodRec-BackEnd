package com.foodrec.backend.MealAPI.command.create_meal;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.MealAPI.dto.CreateMealDTO;
import com.foodrec.backend.MealAPI.dto.MealDTO;
import com.foodrec.backend.MealAPI.entity.Meal;
import com.foodrec.backend.MealAPI.repository.MealRepository;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import com.foodrec.backend.Utils.IdGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateMealCommandHandler implements Command.Handler<CreateMealCommand, MealDTO> {
    private final MealRepository mealRepository;
    private final PostRepository postRepository;
    private final RecipeRepository recipeRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public CreateMealCommandHandler(MealRepository mealRepository, PostRepository postRepository, RecipeRepository recipeRepository, TagRepository tagRepository, ModelMapper modelMapper) {
        this.mealRepository = mealRepository;
        this.postRepository = postRepository;
        this.recipeRepository = recipeRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public MealDTO handle(CreateMealCommand command) {
        CreateMealDTO createMealDTO = command.getCreateMealDTO();
        if (createMealDTO.getMealName().isBlank() || createMealDTO.getMealName() == null
                || createMealDTO.getTagIds() == null || createMealDTO.getTagIds().isEmpty() || createMealDTO.getMaxCalories() <= 50) {
            throw new InvalidDataExceptionHandler("Invalid Data!");
        }
        List<Recipe> recipes = recipeRepository.getRecipesByRecipeTags_Tag_TagIdInAndStatus(createMealDTO.getTagIds(), true);
        List<String> recipeIds = recipes.stream()
                .map(Recipe::getRecipeId)
                .collect(Collectors.toList());
        List<Post> posts = postRepository.getPostsByRecipeIdInAndStatus(recipeIds, 2);
        List<PostDTO> postDTOs = new ArrayList<>(posts.stream()
                .map(post -> {
                    PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                    List<Tag> tagList = tagRepository.findTagsByRecipeTags_Recipe_RecipeId(postDTO.getRecipeId());
                    List<TagDTO> tagDTOList = tagList.stream().map(tag -> modelMapper.map(tag, TagDTO.class)).toList();
                    postDTO.setTagDTOList(tagDTOList);
                    postDTO.setPostStatus(PostStatus.convertStatusToEnum(post.getStatus()));
                    return postDTO;
                })
                .toList());
        // Sorting the list by average score in ascending order
        postDTOs.sort(Comparator.comparingDouble(PostDTO::getAverageScore).reversed());
        List<PostDTO> postDTOList = new ArrayList<>();
        int totalCalories = 0;
        for (PostDTO postDTO : postDTOs) {
            if (totalCalories + postDTO.getCalories() <= createMealDTO.getMaxCalories()) {
                totalCalories += postDTO.getCalories();
                postDTOList.add(postDTO);
            }
        }
        MealDTO mealDTO = new MealDTO();
        if(postDTOList.isEmpty()) {
            mealDTO.setMessage("No recipes found!");
            return mealDTO;
        }
        String mealId = IdGenerator.generateNextId(Meal.class, "mealId");
        mealDTO.setMealId(mealId);
        mealDTO.setMealName(createMealDTO.getMealName());
        mealDTO.setCurrentCalories(totalCalories);
        mealDTO.setPostDTOList(postDTOList);
        if (createMealDTO.getMaxCalories() - totalCalories > 200) {
            mealDTO.setMessage("Not enough calories!");
            return mealDTO;
        }
        mealDTO.setMessage("OK!");
        return mealDTO;
    }
}
