package com.foodrec.backend.CollectionAPI.dto;

public class UpdateCollectionDTO {
    private String collectionId;
    private String collectionName;
    private String description;

    public UpdateCollectionDTO() {
    }

    public UpdateCollectionDTO(String collectionId, String collectionName, String description) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.description = description;
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
}
