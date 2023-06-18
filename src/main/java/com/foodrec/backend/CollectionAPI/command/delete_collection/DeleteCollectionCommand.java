package com.foodrec.backend.CollectionAPI.command.delete_collection;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.DeleteCollectionDTO;
import org.springframework.http.HttpStatus;

public class DeleteCollectionCommand implements Command<HttpStatus> {
    private final DeleteCollectionDTO collectionDTO;
    private String userId;

    public DeleteCollectionCommand(DeleteCollectionDTO collectionDTO, String userId) {
        this.collectionDTO = collectionDTO;
        this.userId = userId;
    }

    public DeleteCollectionDTO getCollectionDTO() {
        return collectionDTO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
