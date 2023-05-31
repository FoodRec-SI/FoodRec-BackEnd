package com.foodrec.backend.PostAPI.service;

import com.foodrec.backend.PostAPI.dto.PostDTO;

public interface PostCommandService {
    boolean createPost(PostDTO postDTO);

    boolean updateStatusPost(String userid, String postid);

    boolean removePostByUser(String userid, String postid);

}