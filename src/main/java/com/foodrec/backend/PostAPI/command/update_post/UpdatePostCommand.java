package com.foodrec.backend.PostAPI.command.update_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.dto.UpdatePostDTO;

public class UpdatePostCommand implements Command<PostDTO> {
    private final UpdatePostDTO updatePostDTO;
    private String userId;

    public UpdatePostCommand(UpdatePostDTO updatePostDTO, String userId) {
        this.updatePostDTO = updatePostDTO;
        this.userId = userId;
    }

    public UpdatePostDTO getUpdatePostDTO() {
        return updatePostDTO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
