package com.foodrec.backend.PostAPI.query.get_post_by_recipe_id;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostByRecipeIdQuery implements Command<Boolean> {
    private String recipeId;
    private String userId;
}
