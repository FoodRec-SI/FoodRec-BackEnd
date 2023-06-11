package com.foodrec.backend.PostAPI.query.get_all_posts;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllPostsApprovedQueryHandler implements Command.Handler<GetAllPostsApprovedQuery, Page<PostDTO>> {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public GetAllPostsApprovedQueryHandler(ModelMapper modelMapper, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public Page<PostDTO> handle(GetAllPostsApprovedQuery command) {
        if (command.getPageNumber() < 0 || command.getPageSize() < 1) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Pageable pageable = PageRequest.of(command.getPageNumber(), command.getPageSize(), Sort.by("time").descending());
        Page<Post> postsPage = postRepository.findAllByStatus(2, pageable);

        List<PostDTO> postDTOS = postsPage.getContent().stream().map(post -> {
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            postDTO.setPostStatus(PostStatus.convertStatusToEnum(post.getStatus()));
            return postDTO;
        }).toList();
        return new PageImpl<>(postDTOS, pageable, postsPage.getTotalElements());
    }
}