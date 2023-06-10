package com.foodrec.backend.RecipeAPI.query.get_all;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import org.springframework.data.domain.Page;

public class GetAllRecipesQuery implements Command<Page<RecipeDTO>>
{
    private final String pageNumber;
    private final String pageSize;

    public GetAllRecipesQuery(String pageNumber, String pageSize)
        {
        this.pageNumber = pageNumber;

        this.pageSize = pageSize;

    }

    public String getPageNumber() {
        return pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }
}
