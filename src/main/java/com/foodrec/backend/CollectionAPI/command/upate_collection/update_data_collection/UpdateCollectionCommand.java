package com.foodrec.backend.CollectionAPI.command.upate_collection.update_data_collection;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.UpdateCollectionDTO;
import org.springframework.http.HttpStatus;

public class UpdateCollectionCommand implements Command<HttpStatus> {
    private final UpdateCollectionDTO collectionDTO;
    private String userId;

    public UpdateCollectionCommand(UpdateCollectionDTO collectionDTO, String userId) {
        this.collectionDTO = collectionDTO;
        this.userId = userId;
    }

    public UpdateCollectionDTO getCollectionDTO() {
        return collectionDTO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
