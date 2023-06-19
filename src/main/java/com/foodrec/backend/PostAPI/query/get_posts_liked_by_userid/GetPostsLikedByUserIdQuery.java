package com.foodrec.backend.PostAPI.query.get_posts_liked_by_userid;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import org.springframework.data.domain.Page;

public class GetPostsLikedByUserIdQuery implements Command<Page<PostDTO>> {
    private String userId;
    private String pageNumber;
    private String pageSize;
    public GetPostsLikedByUserIdQuery(String userId, String pageNumber, String pageSize){
        this.userId = userId;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public String getUserId() {
        return userId;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }
}
