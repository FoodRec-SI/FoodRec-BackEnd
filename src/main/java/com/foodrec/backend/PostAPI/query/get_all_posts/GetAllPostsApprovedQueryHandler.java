package com.foodrec.backend.PostAPI.query.get_all_posts;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
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
public class GetAllPostsApprovedQueryHandler implements Command.Handler<GetAllPostsApprovedQuery, Page<PostDTO>> {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    public GetAllPostsApprovedQueryHandler(ModelMapper modelMapper, PostRepository postRepository, TagRepository tagRepository, AccountRepository accountRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Page<PostDTO> handle(GetAllPostsApprovedQuery query) {
        if (query.getPageNumber() < 0 || query.getPageSize() < 1) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), Sort.by("createdTime").descending());
        Page<Post> postsPage = postRepository.findAllByStatus(2, pageable);
        List<PostDTO> postDTOS = postsPage.getContent().stream().map(post -> {
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            List<Tag> tagList = tagRepository.findTagsByRecipeTags_Recipe_RecipeId(postDTO.getRecipeId());
            List<TagDTO> tagDTOList = tagList.stream().map(tag -> modelMapper.map(tag, TagDTO.class)).toList();
            postDTO.setTagDTOList(tagDTOList);
            postDTO.setModeratorName(accountRepository.findById(postDTO.getModeratorId()).get().getName());
            postDTO.setUserName(accountRepository.findById(postDTO.getUserId()).get().getName());
            postDTO.setPostStatus(PostStatus.convertStatusToEnum(post.getStatus()));
            return postDTO;
        }).toList();
        return new PageImpl<>(postDTOS, pageable, postsPage.getTotalElements());
    }
}