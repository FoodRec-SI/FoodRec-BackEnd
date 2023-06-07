package com.foodrec.backend.RecipeAPI.query.get_all;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.ReadRecipeDTO;
import org.springframework.data.domain.Page;

import java.text.NumberFormat;

public class GetAllRecipesQuery implements Command<Page<ReadRecipeDTO>>
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
