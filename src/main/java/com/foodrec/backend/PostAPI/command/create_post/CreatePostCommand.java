package com.foodrec.backend.PostAPI.command.create_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.CreatePostDTO;

public class CreatePostCommand implements Command<String> {
    private final CreatePostDTO createPostDTO;
    private String userId;

    public CreatePostCommand(CreatePostDTO createPostDTO, String userId) {
        this.createPostDTO = createPostDTO;
        this.userId = userId;
    }

    public CreatePostDTO getCreatePostDTO() {
        return createPostDTO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
