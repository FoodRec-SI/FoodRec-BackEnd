package com.foodrec.backend.PostAPI.command.create_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.CreatePostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.IdGenerator;
import com.foodrec.backend.exception.InvalidDataExceptionHandler;
import com.foodrec.backend.exception.NotFoundExceptionHandler;
import com.foodrec.backend.exception.UnauthorizedExceptionHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Component
public class CreatePostCommandHandler implements Command.Handler<CreatePostCommand, Boolean> {
    private final RecipeRepository recipeRepository;
    private final PostRepository postRepository;

    public CreatePostCommandHandler(RecipeRepository recipeRepository,
                                    PostRepository postRepository) {
        this.recipeRepository = recipeRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    @Override
    public Boolean handle(CreatePostCommand command) {
        CreatePostDTO createPostDTO = command.getCreatePostDTO();
        String postId = IdGenerator.generateNextId(Post.class, "postId");
        LocalDateTime localDateTime = LocalDateTime.now();
        if (createPostDTO.getRecipeId() == null || createPostDTO.getUserName() == null) {
            throw new InvalidDataExceptionHandler("Invalid post!");
        }
        Post post = new Post();
        Optional<Recipe> optionalRecipe = recipeRepository.findById(createPostDTO.getRecipeId());
        if (optionalRecipe.isEmpty()) {
            throw new NotFoundExceptionHandler("Recipe not found!");
        }
        if (!Objects.equals(createPostDTO.getUserName(), optionalRecipe.get().getUsername())) {
            throw new UnauthorizedExceptionHandler("You are not allowed to create post!");
        }
        // Add new data for Post entity
        post.setPostId(postId);
        post.setUserName(createPostDTO.getUserName());
        post.setTime(localDateTime);
        post.setStatus(PostStatus.PENDING_APPROVAL.getValue());
        post.setRecipeName(optionalRecipe.get().getRecipeName());
        post.setDescription(optionalRecipe.get().getDescription());
        post.setCalories(optionalRecipe.get().getCalories());
        post.setDuration(optionalRecipe.get().getDuration());
        post.setImage(optionalRecipe.get().getImage());
        postRepository.save(post);
        return true;
    }
}
