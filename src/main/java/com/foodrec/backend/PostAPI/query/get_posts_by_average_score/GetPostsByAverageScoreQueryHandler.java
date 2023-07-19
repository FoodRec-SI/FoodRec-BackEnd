package com.foodrec.backend.PostAPI.query.get_posts_by_average_score;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PopularPostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;

@Component
public class GetPostsByAverageScoreQueryHandler implements Command.Handler<GetPostsByAverageScoreQuery, Page<PopularPostDTO>> {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public GetPostsByAverageScoreQueryHandler(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Page<PopularPostDTO> handle(GetPostsByAverageScoreQuery query) {
        if (query.getPageNumber() < 0 || query.getPageSize() < 1) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), Sort.by("averageScore").descending());
        Page<Post> postsPage = postRepository.getPostByAverageScoreGreaterThanEqualAndStatus(4, PostStatus.APPROVED.getValue(), pageable);
        List<PopularPostDTO> popularPostDTOList = new ArrayList<>(postsPage.getContent().stream().map(post -> modelMapper.map(post, PopularPostDTO.class)).toList());
        shuffle(popularPostDTOList);
        return new PageImpl<>(popularPostDTOList, pageable, postsPage.getTotalElements());
    }
}
