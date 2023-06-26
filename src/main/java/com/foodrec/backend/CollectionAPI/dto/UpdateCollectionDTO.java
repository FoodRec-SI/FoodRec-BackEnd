package com.foodrec.backend.CollectionAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCollectionDTO {
    private String collectionId;
    private String collectionName;
    private String description;
}
