package com.foodrec.backend.CollectionAPI.command.upate_collection.remove_post_from_collection;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.RemovePostCollectionDTO;
import org.springframework.http.HttpStatus;

public class RemovePostCommand implements Command<HttpStatus> {
    private final RemovePostCollectionDTO removePostCollectionDTO;
    private String userId;

    public RemovePostCommand(RemovePostCollectionDTO removePostCollectionDTO, String userId) {
        this.removePostCollectionDTO = removePostCollectionDTO;
        this.userId = userId;
    }

    public RemovePostCollectionDTO getRemovePostCollectionDTO() {
        return removePostCollectionDTO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
