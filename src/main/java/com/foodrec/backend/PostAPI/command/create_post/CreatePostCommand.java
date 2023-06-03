package com.foodrec.backend.PostAPI.command.create_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.CreatePostDTO;

public class CreatePostCommand implements Command<Boolean> {

    private final CreatePostDTO createPostDTO;

    public CreatePostCommand(CreatePostDTO createPostDTO) {
        this.createPostDTO = createPostDTO;
    }

    public CreatePostDTO getCreatePostDTO() {
        return createPostDTO;
    }
}
