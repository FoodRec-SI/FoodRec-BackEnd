package com.foodrec.backend.CollectionAPI.dto;

public class CollectionDTO {
    private String collectionId;
    private String collectionName;
    private String description;
    private String userId;

    public CollectionDTO() {
    }

    public CollectionDTO(String collectionId, String collectionName, String description, String userId) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.description = description;
        this.userId = userId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
