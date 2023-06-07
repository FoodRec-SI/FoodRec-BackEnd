package com.foodrec.backend.RecipeAPI.query.get_recipe_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.DeleteRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.ReadRecipeDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Cú pháp: Command<Kiểu_dl_trả_về_front-end>
//Phần bên dưới chứa đầu vào của Command.
public class GetRecipeByIdQuery implements Command<ReadRecipeDTO> {
    private final String recipeid;

    public GetRecipeByIdQuery(String recipeid) {
        this.recipeid = recipeid;
    }

    public String getRecipeid() {
        return recipeid;
    }
}
