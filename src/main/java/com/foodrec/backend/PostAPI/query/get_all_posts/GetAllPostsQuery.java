package com.foodrec.backend.PostAPI.query.get_all_posts;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public class GetAllPostsQuery implements Command<Page<PostDTO>> {
    private final int pageNumber;
    private final int pageSize;

    public GetAllPostsQuery(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
}