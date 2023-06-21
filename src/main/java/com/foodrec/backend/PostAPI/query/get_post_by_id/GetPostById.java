package com.foodrec.backend.PostAPI.query.get_post_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostById implements Command<PostDTO> {
    private String postId;
}
