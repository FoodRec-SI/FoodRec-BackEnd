package com.foodrec.backend.RecipeAPI.query.get_all;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;
import org.springframework.data.domain.Page;

public class GetAllRecipesQuery implements Command<Page<RUDRecipeDTO>> {
    private final int pageNumber;
    private final int pageSize;

    public GetAllRecipesQuery(int pageNumber, int pageSize) {
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
