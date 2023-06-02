package com.foodrec.backend.PostAPI.command.update_post;

import an.awesome.pipelinr.Command;

public class UpdatePostCommand implements Command<Boolean> {
    private final String postid;
    private final String moderatorid;
    private final int status;

    public UpdatePostCommand(String postid, String moderatorid, int status) {
        this.postid = postid;
        this.moderatorid = moderatorid;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getPostid() {
        return postid;
    }

    public String getModeratorid() {
        return moderatorid;
    }
}
