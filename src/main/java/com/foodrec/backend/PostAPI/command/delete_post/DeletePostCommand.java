package com.foodrec.backend.PostAPI.command.delete_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;

public class DeletePostCommand implements Command<Boolean> {
    private final String postid;
    private final String userid;//abc

    public DeletePostCommand(String postid, String userid) {
        this.postid = postid;
        this.userid = userid;
    }

    public String getPostid() {
        return postid;
    }

    public String getUserid() {
        return userid;
    }
}
