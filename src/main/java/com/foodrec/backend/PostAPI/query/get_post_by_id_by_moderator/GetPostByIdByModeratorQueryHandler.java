package com.foodrec.backend.PostAPI.query.get_post_by_id_by_moderator;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@CacheConfig(cacheNames = "postModerator")
public class GetPostByIdByModeratorQueryHandler implements Command.Handler<GetPostByIdByModeratorQuery, PostDTO> {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public GetPostByIdByModeratorQueryHandler(PostRepository postRepository, TagRepository tagRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Cacheable(cacheNames = "postModerator", key = "#query.getPostId()")
    @Override
    public PostDTO handle(GetPostByIdByModeratorQuery query) {
        if (query.getPostId().isEmpty()) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Optional<Post> optionalPost = postRepository.findById(query.getPostId());
        if (optionalPost.isEmpty()) {
            throw new NotFoundExceptionHandler("Post not found!");
        }
        List<Tag> tagList = tagRepository.findTagsByRecipeTags_Recipe_RecipeId(optionalPost.get().getRecipeId());
        List<TagDTO> tagDTOList = tagList.stream().map(tag -> modelMapper.map(tag, TagDTO.class)).toList();
        PostDTO postDTO = modelMapper.map(optionalPost.get(), PostDTO.class);
        postDTO.setTagDTOList(tagDTOList);
        postDTO.setPostStatus(PostStatus.convertStatusToEnum(optionalPost.get().getStatus()));
        return postDTO;
    }
}
