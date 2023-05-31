package com.foodrec.backend.PostAPI.query.get_all_posts;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.service.PostQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllPostsQueryHandler implements Command.Handler<GetAllPostsQuery, List<PostDTO>> {

    @Autowired
    private PostQueryService postQueryService;

    @Override
    public List<PostDTO> handle(GetAllPostsQuery command) {
        return postQueryService.getAllPosts();
    }
}