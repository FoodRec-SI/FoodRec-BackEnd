package com.foodrec.backend.PostAPI.query.get_all_posts;


import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import org.springframework.data.domain.Page;

public class GetAllPostsApprovedQuery implements Command<Page<PostDTO>> {
    private int pageNumber;
    private int pageSize;

    public GetAllPostsApprovedQuery() {
    }

    public GetAllPostsApprovedQuery(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}