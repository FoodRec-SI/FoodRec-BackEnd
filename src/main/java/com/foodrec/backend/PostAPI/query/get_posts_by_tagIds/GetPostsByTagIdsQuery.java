package com.foodrec.backend.PostAPI.query.get_posts_by_tagIds;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import org.springframework.data.domain.Page;

import java.util.Collection;

public class GetPostsByTagIdsQuery implements Command<Page<PostDTO>> {
    private int pageNumber;
    private int pageSize;
    private Collection<String> tagIds;

    public GetPostsByTagIdsQuery() {
    }

    public GetPostsByTagIdsQuery(int pageNumber, int pageSize, Collection<String> tagIds) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.tagIds = tagIds;
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

    public Collection<String> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Collection<String> tagIds) {
        this.tagIds = tagIds;
    }
}
