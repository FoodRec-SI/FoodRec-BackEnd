package com.foodrec.backend.PostAPI.query.get_all_posts;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.ViewPostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetAllPostsQueryHandler implements Command.Handler<GetAllPostsQuery, Page<ViewPostDTO>> {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public GetAllPostsQueryHandler(ModelMapper modelMapper, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public Page<ViewPostDTO> handle(GetAllPostsQuery command) {
        Pageable pageable = PageRequest.of(command.getPageNumber(), command.getPageSize(), Sort.by("time").descending());
        Page<Post> postsPage = postRepository.findAllByStatus(2, pageable);
        List<ViewPostDTO> viewPostDTOS = postsPage.getContent().stream()
                .map(post -> modelMapper.map(post, ViewPostDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(viewPostDTOS, pageable, postsPage.getTotalElements());
    }

}