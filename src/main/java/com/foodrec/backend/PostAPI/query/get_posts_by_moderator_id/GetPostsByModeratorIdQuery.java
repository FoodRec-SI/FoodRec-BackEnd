package com.foodrec.backend.PostAPI.query.get_posts_by_moderator_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.ModeratorPostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostsByModeratorIdQuery implements Command<Page<ModeratorPostDTO>> {
    private String moderatorId;
    private int pageNumber;
    private int pageSize;
}
