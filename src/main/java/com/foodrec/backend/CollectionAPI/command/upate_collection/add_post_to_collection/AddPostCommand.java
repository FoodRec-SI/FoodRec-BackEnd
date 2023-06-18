package com.foodrec.backend.CollectionAPI.command.upate_collection.add_post_to_collection;

import an.awesome.pipelinr.Command;
import org.springframework.http.HttpStatus;
import com.foodrec.backend.CollectionAPI.dto.PostCollectionDTO;

public class AddPostCommand implements Command<HttpStatus> {
    private final PostCollectionDTO postCollectionDTO;
    private String userId;

    public AddPostCommand(PostCollectionDTO postCollectionDTO, String userId) {
        this.postCollectionDTO = postCollectionDTO;
        this.userId = userId;
    }

    public PostCollectionDTO getPostCollectionDTO() {
        return postCollectionDTO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
