package com.foodrec.backend.PostAPI.command.create_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.CreatePostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeMongoRepository;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.IdGenerator;
import com.foodrec.backend.exception.DuplicateExceptionHandler;
import com.foodrec.backend.exception.InvalidDataExceptionHandler;
import com.foodrec.backend.exception.NotFoundExceptionHandler;
import com.foodrec.backend.exception.UnauthorizedExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CreatePostCommandHandler implements Command.Handler<CreatePostCommand, Boolean> {
    private final RecipeRepository recipeRepository;
    private final RecipeMongoRepository recipeMongoRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public CreatePostCommandHandler(RecipeRepository recipeRepository,
                                    RecipeMongoRepository recipeMongoRepository,
                                    PostRepository postRepository,
                                    ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMongoRepository = recipeMongoRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean handle(CreatePostCommand command) {
        CreatePostDTO createPostDTO = command.getCreatePostDTO();
        boolean isCreated = true;
        try {
            if (createPostDTO.getRecipeid() == null || createPostDTO.getUserid() == null) {
                throw new InvalidDataExceptionHandler("Invalid Post!");
            }
            //Check duplicate Recipe
            List<Post> existingPost = postRepository.findAll();
            for (Post value : existingPost) {
                if (value.getRecipeid().equals(createPostDTO.getRecipeid())) {
                    throw new DuplicateExceptionHandler("Duplicate Recipe ID");
                }
            }
            //Convert PostDTO to Post entity
            Post post = modelMapper.map(createPostDTO, Post.class);
            //Add new data for Post entity
            String postId = IdGenerator.generateNextId(Post.class, "postid");
            post.setPostid(postId);
            LocalDateTime localDateTime = LocalDateTime.now();
            post.setTime(localDateTime);
            post.setStatus(1); // Set status is pending censorship
            //Save the new Post to the database using PostRepository
            postRepository.save(post);
            Optional<Recipe> optionalRecipe = recipeRepository.findById(post.getRecipeid());
            if(!Objects.equals(post.getUserid(), optionalRecipe.get().getUserid())){
                throw new UnauthorizedExceptionHandler("You are not allowed to create post!");
            }
            recipeMongoRepository.save(optionalRecipe.get());
            //Check the new Post
            if (postRepository.findById(postId).isEmpty()) {
                throw new NotFoundExceptionHandler("The post you just created cannot be found!");
            }
        } catch (InvalidDataExceptionHandler invalidDataExceptionHandler) {
            throw new InvalidDataExceptionHandler("Invalid Post Data!");
        }
        return isCreated;
    }
}
