package com.foodrec.backend.PostAPI.command.create_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.CreatePostDTO;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CreatePostCommand implements Command<PostDTO> {
    private final CreatePostDTO createPostDTO;
    private final String userId;
}
