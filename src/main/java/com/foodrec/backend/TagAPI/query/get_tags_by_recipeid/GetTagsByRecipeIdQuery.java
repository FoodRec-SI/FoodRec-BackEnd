package com.foodrec.backend.TagAPI.query.get_tags_by_recipeid;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
public class GetTagsByRecipeIdQuery implements Command<List<TagDTO>> {
    private final String recipeId;
}
