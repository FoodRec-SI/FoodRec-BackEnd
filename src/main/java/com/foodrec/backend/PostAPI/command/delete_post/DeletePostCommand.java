package com.foodrec.backend.PostAPI.command.delete_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.DeletePostDTO;

public class DeletePostCommand implements Command<Boolean> {
    private final DeletePostDTO deletePostDTO;
    private String userId;


    public DeletePostCommand(DeletePostDTO deletePostDTO, String userId) {
        this.deletePostDTO = deletePostDTO;
        this.userId = userId;
    }

    public DeletePostDTO getDeletePostDTO() {
        return deletePostDTO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
