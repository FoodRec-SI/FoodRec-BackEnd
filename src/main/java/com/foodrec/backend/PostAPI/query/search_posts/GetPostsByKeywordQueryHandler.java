package com.foodrec.backend.PostAPI.query.search_posts;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.PostELK;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostElasticsearchRepository;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetPostsByKeywordQueryHandler implements Command.Handler<GetPostsByKeywordQuery, Page<PostDTO>> {
    private final PostRepository postRepository;
    private final PostElasticsearchRepository postElasticsearchRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    public GetPostsByKeywordQueryHandler(PostRepository postRepository, PostElasticsearchRepository postElasticsearchRepository, TagRepository tagRepository, ModelMapper modelMapper, AccountRepository accountRepository) {
        this.postRepository = postRepository;
        this.postElasticsearchRepository = postElasticsearchRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public Page<PostDTO> handle(GetPostsByKeywordQuery query) {
        if (query.getPageNumber() < 0 || query.getPageSize() < 1 || query.getKeyword().isBlank()) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), Sort.by("createdTime").descending());
        Page<PostELK> postsPage = postElasticsearchRepository.searchPostELKSByPostIdOrRecipeNameLike(query.getKeyword(), query.getKeyword(), pageable);
        if (postsPage.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found!");
        }
        List<PostDTO> postDTOS = postsPage.getContent().stream().map(postELK -> {
            PostDTO postDTO = new PostDTO();
            postDTO.setPostId(postELK.getPostId());
            postDTO.setUserName(postELK.getUserName());
            postDTO.setModeratorName(postELK.getModeratorName());
            postDTO.setRecipeId(postELK.getRecipeId());
            postDTO.setRecipeName(postELK.getRecipeName());
            postDTO.setDescription(postELK.getDescription());
            postDTO.setCalories(postELK.getCalories());
            postDTO.setDuration(postELK.getDuration());
            postDTO.setImage(postDTO.getImage());
            postDTO.setCreatedTime(postELK.getCreatedTime());
            postDTO.setVerifiedTime(postELK.getVerifiedTime());
            postDTO.setAverageScore(postELK.getAverageScore());
            postDTO.setIngredientList(postELK.getIngredientList());
            postDTO.setInstruction(postELK.getInstruction());
            List<Tag> tagList = tagRepository.findTagsByRecipeTags_Recipe_RecipeId(postDTO.getRecipeId());
            List<TagDTO> tagDTOList = tagList.stream().map(tag -> modelMapper.map(tag, TagDTO.class)).toList();
            postDTO.setPostStatus(PostStatus.APPROVED);
            postDTO.setTagDTOList(tagDTOList);
            return postDTO;
        }).toList();
        return new PageImpl<>(postDTOS, pageable, postsPage.getTotalElements());
    }
}
