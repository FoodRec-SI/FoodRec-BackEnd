package com.foodrec.backend.CollectionAPI.command.create_collection;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.CreateCollectionDTO;
import org.springframework.http.HttpStatus;

public class CreateCollectionCommand implements Command<HttpStatus> {
    private final CreateCollectionDTO createCollectionDTO;
    private String userId;

    public CreateCollectionCommand(CreateCollectionDTO createCollectionDTO, String userId) {
        this.createCollectionDTO = createCollectionDTO;
        this.userId = userId;
    }

    public CreateCollectionDTO getCreateCollectionDTO() {
        return createCollectionDTO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
