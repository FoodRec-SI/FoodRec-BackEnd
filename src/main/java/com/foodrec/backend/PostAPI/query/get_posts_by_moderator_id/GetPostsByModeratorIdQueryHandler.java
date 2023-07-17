package com.foodrec.backend.PostAPI.query.get_posts_by_moderator_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.PostAPI.dto.ModeratorPostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@CacheConfig(cacheNames = "postCache")
public class GetPostsByModeratorIdQueryHandler implements Command.Handler<GetPostsByModeratorIdQuery, Page<ModeratorPostDTO>> {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    public GetPostsByModeratorIdQueryHandler(PostRepository postRepository,
                                             ModelMapper modelMapper,
                                             AccountRepository accountRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public Page<ModeratorPostDTO> handle(GetPostsByModeratorIdQuery query) {
        if (query.getPageNumber() < 0 || query.getPageSize() < 1) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), Sort.by("verifiedTime").descending());

        Page<Post> postsPage = postRepository.getPostsByModeratorIdAndStatusIn(query.getModeratorId(),
                Arrays.asList(PostStatus.DELETED.getValue(), PostStatus.APPROVED.getValue()), pageable);
        List<ModeratorPostDTO> moderatorPostDTOS = postsPage.stream().map(post -> {
            ModeratorPostDTO moderatorPostDTO = modelMapper.map(post, ModeratorPostDTO.class);
            moderatorPostDTO.setPostStatus(PostStatus.convertStatusToEnum(post.getStatus()));
            moderatorPostDTO.setUserName(accountRepository.findById(post.getUserId()).get().getName());
            return moderatorPostDTO;
        }).toList();
        return new PageImpl<>(moderatorPostDTOS, pageable, postsPage.getTotalElements());
    }
}
