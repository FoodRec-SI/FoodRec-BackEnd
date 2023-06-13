package com.foodrec.backend.PostAPI.query.get_post_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;

public class GetPostById implements Command<PostDTO> {
    private String postId;

    public GetPostById() {
    }

    public GetPostById(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
