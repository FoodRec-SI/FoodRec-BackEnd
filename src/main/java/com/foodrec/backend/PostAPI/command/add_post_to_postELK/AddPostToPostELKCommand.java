package com.foodrec.backend.PostAPI.command.add_post_to_postELK;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPostToPostELKCommand implements Command<Boolean> {
    private String userId;
}
