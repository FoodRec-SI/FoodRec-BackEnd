package com.foodrec.backend.PostAPI.query.get_posts_by_recipe_name.get_posts_by_status_by_moderator;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public class GetPostByStatusQuery implements Command<Page<PostDTO>> {
    private int pageNumber;
    private int pageSize;

    private List<PostStatus> postStatuses;

    public GetPostByStatusQuery() {
    }

    public GetPostByStatusQuery(int pageNumber, int pageSize, List<PostStatus> postStatuses) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.postStatuses = postStatuses;
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

    public List<PostStatus> getPostStatuses() {
        return postStatuses;
    }

    public void setPostStatuses(List<PostStatus> postStatuses) {
        this.postStatuses = postStatuses;
    }
}
