package com.foodrec.backend.PostAPI.query.get_posts_by_recipe_name;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import org.springframework.data.domain.Page;

public class GetPostsByRecipeNameQuery implements Command<Page<PostDTO>> {
    private int pageNumber;
    private int pageSize;
    private String recipeName;

    public GetPostsByRecipeNameQuery() {
    }

    public GetPostsByRecipeNameQuery(int pageNumber, int pageSize, String recipeName) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.recipeName = recipeName;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
