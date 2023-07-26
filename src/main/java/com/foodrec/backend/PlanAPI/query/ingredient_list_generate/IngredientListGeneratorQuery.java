package com.foodrec.backend.PlanAPI.query.ingredient_list_generate;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientListGeneratorQuery implements Command<String> {
    private String planId;
    private String userId;
}
