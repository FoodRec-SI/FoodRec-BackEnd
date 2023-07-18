package com.foodrec.backend.PostAPI.query.get_all_posts;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PopularPostDTO;
import com.foodrec.backend.PostAPI.dto.SortPostEnum;
import com.foodrec.backend.PostAPI.dto.SortTypeEnum;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllPostsApprovedQueryHandler implements Command.Handler<GetAllPostsApprovedQuery, Page<PopularPostDTO>> {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public GetAllPostsApprovedQueryHandler(ModelMapper modelMapper, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public Page<PopularPostDTO> handle(GetAllPostsApprovedQuery query) {
        if (query.getPageNumber() < 0 || query.getPageSize() < 1) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Sort.Direction direction = Sort.Direction.DESC;
        if (query.getSortType().equals(SortTypeEnum.ACCENDING)) {
            direction = Sort.Direction.ASC;
        }
        Sort sortBy = Sort.by(direction, SortPostEnum.CREATED_TIME.getSortField());
        if (query.getSortPost().equals(SortPostEnum.AVERAGE_SCORE)) {
            sortBy = Sort.by(direction, SortPostEnum.AVERAGE_SCORE.getSortField());
        }
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), sortBy);
        Page<Post> postsPage = postRepository.findAllByStatus(2, pageable);
        List<PopularPostDTO> popularPostDTOS = postsPage.getContent().stream().map(post -> modelMapper.map(post, PopularPostDTO.class)).toList();
        return new PageImpl<>(popularPostDTOS, pageable, postsPage.getTotalElements());
    }
}