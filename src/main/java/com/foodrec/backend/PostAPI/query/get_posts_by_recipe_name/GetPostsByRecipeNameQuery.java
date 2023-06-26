package com.foodrec.backend.PostAPI.query.get_posts_by_recipe_name;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostsByRecipeNameQuery implements Command<Page<PostDTO>> {
    private int pageNumber;
    private int pageSize;
    private String recipeName;
}
