package com.foodrec.backend.PostAPI.query.get_posts_by_status_by_moderator;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostByStatusQuery implements Command<Page<PostDTO>> {
    private int pageNumber;
    private int pageSize;
    private List<PostStatus> postStatuses;
}
