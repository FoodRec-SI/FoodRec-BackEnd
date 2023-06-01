package com.foodrec.backend.PostAPI.command.create_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;

public class CreatePostCommand implements Command<Boolean> {

    private final PostDTO postDTO;

    public CreatePostCommand(PostDTO postDTO) {
        this.postDTO = postDTO;
    }

    public PostDTO getPostDTO() {
        return postDTO;
    }

}
