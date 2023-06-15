package com.foodrec.backend.PostAPI.command.update_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.dto.UpdatePostDTO;

public class UpdatePostCommand implements Command<PostDTO> {
    private final UpdatePostDTO updatePostDTO;
    private String moderatorId;

    public UpdatePostCommand(UpdatePostDTO updatePostDTO, String moderatorId) {
        this.updatePostDTO = updatePostDTO;
        this.moderatorId = moderatorId;
    }

    public UpdatePostDTO getUpdatePostDTO() {
        return updatePostDTO;
    }

    public String getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(String moderatorId) {
        this.moderatorId = moderatorId;
    }
}
