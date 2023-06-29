package com.foodrec.backend.PostAPI.query.get_post_by_id_by_moderator;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostByIdByModeratorQuery implements Command<PostDTO> {
    private String postId;
}
