package com.foodrec.backend.PostAPI.command.update_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.UpdatePostDTO;

public class UpdatePostCommand implements Command<Boolean> {
    private final UpdatePostDTO updatePostDTO;

    public UpdatePostCommand(UpdatePostDTO updatePostDTO) {
        this.updatePostDTO = updatePostDTO;
    }

    public UpdatePostDTO getUpdatePostDTO() {
        return updatePostDTO;
    }
}
