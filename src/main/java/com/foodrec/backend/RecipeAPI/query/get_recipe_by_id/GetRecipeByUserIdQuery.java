package com.foodrec.backend.RecipeAPI.query.get_recipe_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRecipeByUserIdQuery implements Command<Page<RecipeDTO>> {
    private String userid;
    private String pageNumber;
    private String pageSize;
}

