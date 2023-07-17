package com.foodrec.backend.PostAPI.query.get_post_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.LikeAPI.entity.Likes;
import com.foodrec.backend.LikeAPI.entity.LikesCompositeKey;
import com.foodrec.backend.LikeAPI.repository.LikesRepository;
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
@CacheConfig(cacheNames = "postCache")
public class GetPostByIdQueryHandler implements Command.Handler<GetPostByIdQuery, PostDTO> {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    private final LikesRepository likesRepository;
    private final AccountRepository accountRepository;

    public GetPostByIdQueryHandler(PostRepository postRepository,
                                   ModelMapper modelMapper,
                                   LikesRepository likesRepository,
                                   TagRepository tagRepository, AccountRepository accountRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
        this.likesRepository = likesRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Cacheable(cacheNames = "postMember", key = "#query.getPostId()")
    @Override
    public PostDTO handle(GetPostByIdQuery query) {
        if (query.getPostId().isEmpty()) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Optional<Post> optionalPost = postRepository.findPostByPostIdAndStatus(query.getPostId(), 2);
        if (optionalPost.isEmpty()) {
            throw new NotFoundExceptionHandler("Post not found!");
        }
        List<Tag> tagList = tagRepository.findTagsByRecipeTags_Recipe_RecipeId(optionalPost.get().getRecipeId());
        List<TagDTO> tagDTOList = tagList.stream().map(tag -> modelMapper.map(tag, TagDTO.class)).toList();
        PostDTO postDTO = modelMapper.map(optionalPost.get(), PostDTO.class);
        postDTO.setTagDTOList(tagDTOList);
        postDTO.setPostStatus(PostStatus.convertStatusToEnum(optionalPost.get().getStatus()));
        postDTO.setModeratorName(accountRepository.findById(postDTO.getModeratorId()).get().getName());
        postDTO.setUserName(accountRepository.findById(postDTO.getUserId()).get().getName());
        Optional<Likes> foundLike = likesRepository.findById(
                new LikesCompositeKey(
                        query.getUserId(),
                        query.getPostId()
                ));
        if (foundLike.isEmpty()) postDTO.setLiked(false);
        else postDTO.setLiked(true);
        return postDTO;
    }
}
