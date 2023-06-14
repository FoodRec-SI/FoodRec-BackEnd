package com.foodrec.backend.PostAPI.query.get_posts_by_tagIds;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public class GetPostsByTagIdsQuery implements Command<Page<PostDTO>> {
    private int pageNumber;
    private int pageSize;
    private List<String> tagList;

    public GetPostsByTagIdsQuery() {
    }

    public GetPostsByTagIdsQuery(int pageNumber, int pageSize, List<String> tagList) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.tagList = tagList;
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

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
