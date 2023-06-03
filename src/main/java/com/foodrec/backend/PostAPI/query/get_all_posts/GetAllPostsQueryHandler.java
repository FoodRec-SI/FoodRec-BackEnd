package com.foodrec.backend.PostAPI.query.get_all_posts;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetAllPostsQueryHandler implements Command.Handler<GetAllPostsQuery, Page<PostDTO>> {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public GetAllPostsQueryHandler(ModelMapper modelMapper, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public Page<PostDTO> handle(GetAllPostsQuery command) {
        Pageable pageable = PageRequest.of(command.getPageNumber(), command.getPageSize(), Sort.by("time").descending());
        Page<Post> postsPage = postRepository.findAll(pageable);

        List<PostDTO> postDTOs = postsPage.getContent().stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(postDTOs, pageable, postsPage.getTotalElements());
    }

}