package com.foodrec.backend.LikeAPI.command.remove_like;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.LikeAPI.dto.DeleteLikeDTO;

public class RemoveLikeCommand implements Command<Boolean> {
    private String postId;
    private String userId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RemoveLikeCommand(String userId, String postId){
        this.userId = userId;
        this.postId = postId;
    }
}
