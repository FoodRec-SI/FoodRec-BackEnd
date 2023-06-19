package com.foodrec.backend.CollectionAPI.query.get_collections_by_user_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.CollectionDTO;
import org.springframework.data.domain.Page;

public class GetCollectionsByUserIdQuery implements Command<Page<CollectionDTO>> {
    private int pageNumber;
    private int pageSize;
    private String userId;

    public GetCollectionsByUserIdQuery() {
    }

    public GetCollectionsByUserIdQuery(int pageNumber, int pageSize, String userId) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
