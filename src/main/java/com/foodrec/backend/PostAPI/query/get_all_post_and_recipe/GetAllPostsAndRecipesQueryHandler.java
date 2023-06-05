package com.foodrec.backend.PostAPI.query.get_all_post_and_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.CombinedPostRecipeDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeMongoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GetAllPostsAndRecipesQueryHandler implements Command.Handler<GetAllPostsAndRecipesQuery, Page<CombinedPostRecipeDTO>> {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final RecipeMongoRepository recipeMongoRepository;

    public GetAllPostsAndRecipesQueryHandler(PostRepository postRepository, ModelMapper modelMapper, RecipeMongoRepository recipeMongoRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.recipeMongoRepository = recipeMongoRepository;
    }

    @Override
    public Page<CombinedPostRecipeDTO> handle(GetAllPostsAndRecipesQuery command) {
        Pageable pageable = PageRequest.of(command.getPageNumber(), command.getPageSize(), Sort.by("time").descending());
        Page<Post> postPage = postRepository.findAllByStatus(2, pageable);
        List<CombinedPostRecipeDTO> combinedDTOs = new ArrayList<>();
        for (Post post : postPage.getContent()) {
            Optional<Recipe> optionalRecipe = recipeMongoRepository.findByRecipeid(post.getRecipeid());
            System.out.println(recipeMongoRepository.findById(post.getRecipeid()));
            if (optionalRecipe.isPresent()) {
                CombinedPostRecipeDTO combinedPostRecipeDTO = modelMapper.map(post, CombinedPostRecipeDTO.class);
                Recipe recipe = optionalRecipe.get();
                combinedPostRecipeDTO.setRecipeName(recipe.getRecipename());
                combinedPostRecipeDTO.setDescription(recipe.getDescription());
                combinedPostRecipeDTO.setCalories(recipe.getCalories());
                combinedPostRecipeDTO.setDuration(recipe.getDuration());
                combinedPostRecipeDTO.setImage(recipe.getImage());
                combinedDTOs.add(combinedPostRecipeDTO);
            }
        }
        return new PageImpl<>(combinedDTOs, pageable, postPage.getTotalElements());
    }
}
