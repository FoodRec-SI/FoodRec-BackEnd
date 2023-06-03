package com.foodrec.backend.PostAPI.command.delete_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.DeletePostDTO;

public class DeletePostCommand implements Command<Boolean> {
    private final DeletePostDTO deletePostDTO;

    public DeletePostCommand(DeletePostDTO deletePostDTO) {
        this.deletePostDTO = deletePostDTO;
    }

    public DeletePostDTO getDeletePostDTO() {
        return deletePostDTO;
    }
}
