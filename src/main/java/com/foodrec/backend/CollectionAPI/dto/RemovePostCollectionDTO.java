package com.foodrec.backend.CollectionAPI.dto;

public class RemovePostCollectionDTO {
    private String postId;
    private String collectionId;

    public RemovePostCollectionDTO() {
    }

    public RemovePostCollectionDTO(String postId, String collectionId) {
        this.postId = postId;
        this.collectionId = collectionId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
}
