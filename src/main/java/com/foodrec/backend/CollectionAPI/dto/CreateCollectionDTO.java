package com.foodrec.backend.CollectionAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCollectionDTO {
    private String collectionName;
    private String description;
}
