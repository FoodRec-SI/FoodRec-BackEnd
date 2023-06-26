package com.foodrec.backend.PostAPI.query.get_posts_by_tagIds;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostsByTagIdsQuery implements Command<Page<PostDTO>> {
    private int pageNumber;
    private int pageSize;
    private Set<String> tagIds;
}
