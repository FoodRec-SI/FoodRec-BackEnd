package com.foodrec.backend.PostAPI.query.get_posts_by_tagId;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.RecipeAPI.entity.RecipeTag;
import com.foodrec.backend.RecipeAPI.repository.RecipeTagRepository;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetPostsByTagIdQueryHandler implements Command.Handler<GetPostsByTagIdQuery, Page<PostDTO>> {
    private final PostRepository postRepository;
    private final RecipeTagRepository recipeTagRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public GetPostsByTagIdQueryHandler(PostRepository postRepository, RecipeTagRepository recipeTagRepository, TagRepository tagRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.recipeTagRepository = recipeTagRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public Page<PostDTO> handle(GetPostsByTagIdQuery query) {
        if (query.getPageNumber() < 0 || query.getPageSize() < 1 ||
                query.getTagId().isEmpty() || query.getTagId().equals("")) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), Sort.by("createdTime").descending());
        List<RecipeTag> recipes = recipeTagRepository.getRecipeTagsByTag_TagId(query.getTagId());
        List<String> recipeIds = new ArrayList<>();
        for (RecipeTag RecipeTag : recipes) {
            recipeIds.add(RecipeTag.getRecipeTagId().getRecipeId());
        }
        Page<Post> postsPage = postRepository.findPostsByRecipeIdInAndStatus(recipeIds, 2, pageable);
        if (postsPage.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found!");
        }
        List<PostDTO> postDTOS = postsPage.getContent().stream().map(post -> {
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            List<Tag> tagList = tagRepository.findTagsByRecipeTags_Recipe_RecipeId(postDTO.getRecipeId());
            List<TagDTO> tagDTOList = tagList.stream().map(tag -> modelMapper.map(tag, TagDTO.class)).toList();
            postDTO.setTagDTOList(tagDTOList);
            postDTO.setPostStatus(PostStatus.convertStatusToEnum(post.getStatus()));
            return postDTO;
        }).toList();
        return new PageImpl<>(postDTOS, pageable, postsPage.getTotalElements());
    }
}
