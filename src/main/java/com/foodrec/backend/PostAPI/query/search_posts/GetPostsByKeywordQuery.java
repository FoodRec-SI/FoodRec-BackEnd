package com.foodrec.backend.PostAPI.query.search_posts;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PopularPostDTO;
import com.foodrec.backend.PostAPI.dto.SortPostEnum;
import com.foodrec.backend.PostAPI.dto.SortTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostsByKeywordQuery implements Command<Page<PopularPostDTO>> {
    private int pageNumber;
    private int pageSize;
    private String keyword;
    private SortTypeEnum sortType;
    private SortPostEnum sortPost;
}
