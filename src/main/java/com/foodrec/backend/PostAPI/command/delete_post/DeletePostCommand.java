package com.foodrec.backend.PostAPI.command.delete_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.DeletePostDTO;
import org.springframework.http.HttpStatus;

public class DeletePostCommand implements Command<HttpStatus> {
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
