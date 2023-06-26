package com.foodrec.backend.CollectionAPI.query.get_collection_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.CollectionDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCollectionByIdQuery implements Command<CollectionDetailsDTO> {
    private int pageNumber;
    private int pageSize;
    private String userId;
    private String collectionId;
}
