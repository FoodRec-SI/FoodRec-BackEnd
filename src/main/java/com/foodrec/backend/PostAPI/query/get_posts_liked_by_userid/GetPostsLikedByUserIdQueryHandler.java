package com.foodrec.backend.PostAPI.query.get_posts_liked_by_userid;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidPageInfoException;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.Utils.PageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetPostsLikedByUserIdQueryHandler implements Command.Handler
        <GetPostsLikedByUserIdQuery, Page<PostDTO>> {
    private final PostRepository postRepository;
    private final PageUtils pageUtils;
    private final ModelMapper modelMapper;

    public GetPostsLikedByUserIdQueryHandler(PostRepository postRepository, PageUtils pageUtils,
                                             ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.pageUtils = pageUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<PostDTO> handle(GetPostsLikedByUserIdQuery command)
            throws InvalidPageInfoException {

        if (!pageUtils.isNumber(command.getPageNumber())
                || !pageUtils.isNumber(command.getPageSize())) {
            throw new InvalidPageInfoException("pageNumber or pageSize must be an Integer!");
        }
        int pageNumber = Integer.parseInt(command.getPageNumber());
        int pageSize = Integer.parseInt(command.getPageSize());
        if (pageNumber < 0 || pageSize < 0)
            throw new InvalidPageInfoException
                    ("pageNumber or pageSize can't be less than 0.");


        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.by("postId").descending());

        String userId = command.getUserId();

        Page<Post> postPages = postRepository.getPostsByLikes_Account_UserId(userId, pageable);

        List<PostDTO> postDTOList = postPages.getContent().stream()
                .map((post) -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(postDTOList, pageable, postPages.getTotalElements());
    }

}
