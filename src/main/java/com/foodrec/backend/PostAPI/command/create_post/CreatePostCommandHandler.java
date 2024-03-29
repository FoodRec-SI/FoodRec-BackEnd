package com.foodrec.backend.PostAPI.command.create_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.PostAPI.dto.CreatePostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.IdGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Component
public class CreatePostCommandHandler implements Command.Handler<CreatePostCommand, String> {
    private final RecipeRepository recipeRepository;
    private final PostRepository postRepository;

    public CreatePostCommandHandler(RecipeRepository recipeRepository, PostRepository postRepository) {
        this.recipeRepository = recipeRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    @Override
    public String handle(CreatePostCommand command) {
        CreatePostDTO createPostDTO = command.getCreatePostDTO();
        LocalDateTime localDateTime = LocalDateTime.now();
        if (createPostDTO.getRecipeId() == null || command.getUserId() == null) {
            throw new InvalidDataExceptionHandler("Invalid post!");
        }
        Optional<Recipe> optionalRecipe = recipeRepository.findById(createPostDTO.getRecipeId());
        if (optionalRecipe.isEmpty() || !optionalRecipe.get().isStatus()) {
            throw new NotFoundExceptionHandler("Recipe not found!");
        }
        Recipe recipe = optionalRecipe.get();
        recipe.setPublicStatus(true);
        recipeRepository.save(recipe);
        if (!Objects.equals(command.getUserId(), optionalRecipe.get().getUserId())) {
            throw new UnauthorizedExceptionHandler("You are not allowed to create post!");
        }
        Post post = new Post();
        // Add new data for Post entity
        String postId = IdGenerator.generateNextId(Post.class, "postId");
        post.setPostId(postId);
        post.setUserId(command.getUserId());
        post.setRecipeId(createPostDTO.getRecipeId());
        post.setCreatedTime(localDateTime);
        post.setVerifiedTime(null);
        post.setStatus(PostStatus.PENDING_APPROVAL.getValue());
        post.setRecipeName(optionalRecipe.get().getRecipeName());
        post.setDescription(optionalRecipe.get().getDescription());
        post.setCalories(optionalRecipe.get().getCalories());
        post.setDuration(optionalRecipe.get().getDuration());
        post.setImage(optionalRecipe.get().getImage());
        post.setIngredientList(optionalRecipe.get().getIngredientList());
        post.setInstruction(optionalRecipe.get().getInstructions());
        post.setAverageScore(0);
        postRepository.save(post);
        return postId;
    }
}
