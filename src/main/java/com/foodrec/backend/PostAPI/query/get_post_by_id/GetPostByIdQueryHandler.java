package com.foodrec.backend.PostAPI.query.get_post_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.LikeAPI.entity.Likes;
import com.foodrec.backend.LikeAPI.entity.LikesCompositeKey;
import com.foodrec.backend.LikeAPI.repository.LikesRepository;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@CacheConfig(cacheNames = "postCache")
public class GetPostByIdQueryHandler implements Command.Handler<GetPostByIdQuery, PostDTO> {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final LikesRepository likesRepository;

    public GetPostByIdQueryHandler(PostRepository postRepository,
                                   ModelMapper modelMapper,
                                   LikesRepository likesRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.likesRepository = likesRepository;
    }

    @Transactional
    @Cacheable(cacheNames = "post", key = "#query.getPostId()")
    @Override
    public PostDTO handle(GetPostByIdQuery query) {

        if (query.getPostId().isEmpty()) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }

        Optional<Post> optionalPost = postRepository.findPostByPostIdAndStatus(query.getPostId(), 2);

        if (optionalPost.isEmpty()) {
            throw new NotFoundExceptionHandler("Post not found!");
        }
        PostDTO postDTO = modelMapper.map(optionalPost.get(), PostDTO.class);
        postDTO.setPostStatus(PostStatus.convertStatusToEnum(optionalPost.get().getStatus()));

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
