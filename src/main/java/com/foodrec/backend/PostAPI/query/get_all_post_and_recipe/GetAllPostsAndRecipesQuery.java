package com.foodrec.backend.PostAPI.query.get_all_post_and_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.CombinedPostRecipeDTO;
import org.springframework.data.domain.Page;

public class GetAllPostsAndRecipesQuery implements Command<Page<CombinedPostRecipeDTO>> {
    private final int pageNumber;
    private final int pageSize;

    public GetAllPostsAndRecipesQuery(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
}
