package com.foodrec.backend.PostAPI.query.get_posts_by_tagIds;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
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
public class GetPostsByTagIdsQueryHandler implements Command.Handler<GetPostsByTagIdsQuery, Page<PostDTO>> {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final RecipeTagRepository recipeTagRepository;

    public GetPostsByTagIdsQueryHandler(PostRepository postRepository, TagRepository tagRepository, AccountRepository accountRepository, ModelMapper modelMapper, RecipeTagRepository recipeTagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.accountRepository = accountRepository;
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
            List<Tag> tagList = tagRepository.findTagsByRecipeTags_Recipe_RecipeId(postDTO.getRecipeId());
            List<TagDTO> tagDTOList = tagList.stream().map(tag -> modelMapper.map(tag, TagDTO.class)).toList();
            postDTO.setTagDTOList(tagDTOList);
            postDTO.setUserName(accountRepository.findById(postDTO.getUserId()).get().getName());
            postDTO.setModeratorName(accountRepository.findById(postDTO.getModeratorId()).get().getName());
            postDTO.setPostStatus(PostStatus.convertStatusToEnum(post.getStatus()));
            return postDTO;
        }).toList();
        return new PageImpl<>(postDTOS, pageable, postsPage.getTotalElements());
    }
}
