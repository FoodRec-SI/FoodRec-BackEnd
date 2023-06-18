package com.foodrec.backend.PostAPI.query.get_posts_by_collection_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import org.springframework.data.domain.Page;

public class GetPostByCollectionIdQuery implements Command<Page<PostDTO>> {
    private int pageNumber;
    private int pageSize;
    private String userId;
    private String collectionId;

    public GetPostByCollectionIdQuery() {
    }

    public GetPostByCollectionIdQuery(int pageNumber, int pageSize, String userId, String collectionId) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.userId = userId;
        this.collectionId = collectionId;
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

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
}
