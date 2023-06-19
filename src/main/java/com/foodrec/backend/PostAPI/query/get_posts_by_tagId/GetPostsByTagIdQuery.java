package com.foodrec.backend.PostAPI.query.get_posts_by_tagId;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import org.springframework.data.domain.Page;


public class GetPostsByTagIdQuery implements Command<Page<PostDTO>> {
    private int pageNumber;
    private int pageSize;
    private String tagId;

    public GetPostsByTagIdQuery() {
    }

    public GetPostsByTagIdQuery(int pageNumber, int pageSize, String tagId) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.tagId = tagId;
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

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
