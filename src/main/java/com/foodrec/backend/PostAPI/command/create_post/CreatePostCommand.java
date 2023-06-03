package com.foodrec.backend.PostAPI.command.create_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;

public class CreatePostCommand implements Command<Boolean> {

    private final String recipeid;
    private final String userid;

    public CreatePostCommand(String recipeid, String userid) {
        this.recipeid = recipeid;
        this.userid = userid;
    }

    public String getRecipeid() {
        return recipeid;
    }

    public String getUserid() {
        return userid;
    }
}
