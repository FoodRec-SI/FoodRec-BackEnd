package com.foodrec.backend.PostAPI.query.get_posts_by_tagIds;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.RecipeAPI.entity.RecipeTag;
import com.foodrec.backend.RecipeAPI.repository.RecipeTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetPostsByTagIdsQueryHandler implements Command.Handler<GetPostsByTagIdsQuery, Page<PostDTO>> {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final RecipeTagRepository recipeTagRepository;

    public GetPostsByTagIdsQueryHandler(PostRepository postRepository, ModelMapper modelMapper, RecipeTagRepository recipeTagRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.recipeTagRepository = recipeTagRepository;
    }

    @Transactional
    @Override
    public Page<PostDTO> handle(GetPostsByTagIdsQuery query) {
        if (query.getPageNumber() < 0 || query.getPageSize() < 1 ||
                query.getTagIds().isEmpty() || query.getTagIds().equals("")) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), Sort.by("createdTime").descending());
        List<RecipeTag> recipeTags = recipeTagRepository.getRecipeTagsByTag_TagIdIn(query.getTagIds());
        List<String> recipeIds = new ArrayList<>();
        for (RecipeTag recipeTag : recipeTags) {
            recipeIds.add(recipeTag.getRecipeTagId().getRecipeId());
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
