package com.foodrec.backend.RecipeAPI.query.get_recipe_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import org.springframework.data.domain.Page;

public class GetRecipeByUserIdQuery implements Command<Page<RecipeDTO>> {
    private String userid;
    private String pageNumber;
    private String pageSize;

    public GetRecipeByUserIdQuery(String userid, String pageNumber, String pageSize) {
        this.userid = userid;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public GetRecipeByUserIdQuery() {
    }

    public String getUserid() {
        return userid;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }


}

