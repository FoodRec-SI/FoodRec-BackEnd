package com.foodrec.backend.CollectionAPI.dto;

public class CreateCollectionDTO {
    private String collectionName;
    private String description;

    public CreateCollectionDTO() {
    }

    public CreateCollectionDTO(String collectionName, String description) {
        this.collectionName = collectionName;
        this.description = description;
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
