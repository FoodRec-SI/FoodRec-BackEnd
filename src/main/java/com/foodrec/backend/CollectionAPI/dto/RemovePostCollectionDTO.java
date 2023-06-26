package com.foodrec.backend.CollectionAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemovePostCollectionDTO {
    private String postId;
    private String collectionId;
}
