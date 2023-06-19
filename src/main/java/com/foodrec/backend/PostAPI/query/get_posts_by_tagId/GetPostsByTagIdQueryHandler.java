package com.foodrec.backend.PostAPI.query.get_posts_by_tagId;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Component
public class GetPostsByTagIdQueryHandler implements Command.Handler<GetPostsByTagIdQuery, Page<PostDTO>> {
    private final PostRepository postRepository;
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;

    public GetPostsByTagIdQueryHandler(PostRepository postRepository, RecipeRepository recipeRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public Page<PostDTO> handle(GetPostsByTagIdQuery query) {
        if (query.getPageNumber() < 0 || query.getPageSize() < 1 ||
                query.getTagId().isEmpty() || query.getTagId().equals("")) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), Sort.by("time").descending());
        List<Recipe> recipes = recipeRepository.findRecipesByTagTagId(query.getTagId());
        List<String> recipeIds = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeIds.add(recipe.getRecipeId());
        }
        Page<Post> postsPage = postRepository.findPostsByRecipeIdInAndStatus(recipeIds, 2, pageable);
        if (postsPage.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found!");
        }
        List<PostDTO> postDTOS = postsPage.getContent().stream().map(post -> {
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            postDTO.setPostStatus(PostStatus.convertStatusToEnum(post.getStatus()));
            return postDTO;
        }).toList();
        return new PageImpl<>(postDTOS, pageable, postsPage.getTotalElements());
    }
}
