package com.foodrec.backend.PostAPI.command.create_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.CreatePostDTO;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.IdGenerator;
import com.foodrec.backend.Exception.DuplicateExceptionHandler;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CreatePostCommandHandler implements Command.Handler<CreatePostCommand, PostDTO> {
    private final RecipeRepository recipeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public CreatePostCommandHandler(RecipeRepository recipeRepository,
                                    PostRepository postRepository, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public PostDTO handle(CreatePostCommand command) {
        CreatePostDTO createPostDTO = command.getCreatePostDTO();
        LocalDateTime localDateTime = LocalDateTime.now();
        if (createPostDTO.getRecipeId() == null || createPostDTO.getUserName() == null) {
            throw new InvalidDataExceptionHandler("Invalid post!");
        }
        Post post = new Post();
        Optional<Recipe> optionalRecipe = recipeRepository.findById(createPostDTO.getRecipeId());
        if (optionalRecipe.isEmpty()) {
            throw new NotFoundExceptionHandler("Recipe not found!");
        }
        if (!Objects.equals(createPostDTO.getUserName(), optionalRecipe.get().getUserName())) {
            throw new UnauthorizedExceptionHandler("You are not allowed to create post!");
        }
        List<Post> posts  = postRepository.findPostByRecipeId(createPostDTO.getRecipeId());
        for (Post value : posts) {
            if (value.getRecipeName().toLowerCase().trim().equals(optionalRecipe.get().getRecipeName().toLowerCase().trim()) &&
                    value.getDescription().toLowerCase().trim().equals(optionalRecipe.get().getDescription().toLowerCase().trim())) {
                    throw new DuplicateExceptionHandler("Duplicate recipe!");
            }
        }
        // Add new data for Post entity
        String postId = IdGenerator.generateNextId(Post.class, "postId");
        post.setPostId(postId);
        post.setUserName(createPostDTO.getUserName());
        post.setRecipeId(createPostDTO.getRecipeId());
        post.setTime(localDateTime);
        post.setStatus(PostStatus.PENDING_APPROVAL.getValue());
        post.setRecipeName(optionalRecipe.get().getRecipeName());
        post.setDescription(optionalRecipe.get().getDescription());
        post.setCalories(optionalRecipe.get().getCalories());
        post.setDuration(optionalRecipe.get().getDuration());
        post.setImage(optionalRecipe.get().getImage());
        postRepository.save(post);
        Optional<Post> optionalPost = postRepository.findById(post.getPostId());
        if(optionalPost.isEmpty()){
            throw new NotFoundExceptionHandler("Can not create post!");
        }
        PostDTO postDTO = modelMapper.map(optionalPost.get(), PostDTO.class);
        postDTO.setPostStatus(PostStatus.convertStatusToEnum(optionalPost.get().getStatus()));
        return postDTO;
    }
}
