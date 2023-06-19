package com.foodrec.backend.LikeAPI.command.add_like;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.LikeAPI.dto.LikeDTO;

public class AddLikeCommand implements Command<LikeDTO> {
    private String postId;
    private String userId;
    public AddLikeCommand(String postId,String userId){
        this.postId = postId;
        this.userId = userId;
    }

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
}
