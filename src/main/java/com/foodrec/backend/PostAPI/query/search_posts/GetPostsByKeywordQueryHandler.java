package com.foodrec.backend.PostAPI.query.search_posts;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.PostAPI.dto.SearchPostDTO;
import com.foodrec.backend.PostAPI.dto.SortPostEnum;
import com.foodrec.backend.PostAPI.dto.SortTypeEnum;
import com.foodrec.backend.PostAPI.entity.PostELK;
import com.foodrec.backend.PostAPI.repository.PostElasticsearchRepository;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetPostsByKeywordQueryHandler implements Command.Handler<GetPostsByKeywordQuery, Page<SearchPostDTO>> {
    private final PostElasticsearchRepository postElasticsearchRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public GetPostsByKeywordQueryHandler(PostElasticsearchRepository postElasticsearchRepository, TagRepository tagRepository, ModelMapper modelMapper, AccountRepository accountRepository) {
        this.postElasticsearchRepository = postElasticsearchRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<SearchPostDTO> handle(GetPostsByKeywordQuery query) {
        if (query.getPageNumber() < 0 || query.getPageSize() < 1 || query.getKeyword().isBlank()) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Sort.Direction direction = Sort.Direction.DESC;
        if (query.getSortType().equals(SortTypeEnum.ACCENDING)) {
            direction = Sort.Direction.ASC;
        }
        Sort sortBy = Sort.by(direction, SortPostEnum.CREATED_TIME.getSortField());
        if (query.getSortPost().equals(SortPostEnum.AVERAGE_SCORE)) {
            sortBy = Sort.by(direction, SortPostEnum.AVERAGE_SCORE.getSortField());
        }
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), sortBy);
        Page<PostELK> postsPage = postElasticsearchRepository.searchPostELKSByPostIdOrRecipeNameLike(query.getKeyword(), query.getKeyword(), pageable);
        if (postsPage.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found!");
        }
        List<SearchPostDTO> searchPostDTOS = postsPage.getContent().stream().map(postELK -> {
            SearchPostDTO searchPostDTO = new SearchPostDTO();
            searchPostDTO.setPostId(postELK.getPostId());
            searchPostDTO.setRecipeName(postELK.getRecipeName());
            searchPostDTO.setDuration(postELK.getDuration());
            searchPostDTO.setImage(postELK.getImage());
            searchPostDTO.setAverageScore(postELK.getAverageScore());
            List<Tag> tagList = tagRepository.findTagsByRecipeTags_Recipe_RecipeId(postELK.getRecipeId());
            searchPostDTO.setTagDTOList(tagList.stream().map(tag -> modelMapper.map(tag, TagDTO.class)).toList());
            return searchPostDTO;
        }).toList();
        return new PageImpl<>(searchPostDTOS, pageable, postsPage.getTotalElements());
    }
}
