package com.foodrec.backend.PostAPI.query.get_posts_by_average_score;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PopularPostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostsByAverageScoreQuery implements Command<Page<PopularPostDTO>> {
    private int pageNumber;
    private int pageSize;
}
