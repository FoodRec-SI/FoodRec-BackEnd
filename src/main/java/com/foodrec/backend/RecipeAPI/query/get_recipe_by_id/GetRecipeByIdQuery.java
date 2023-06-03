package com.foodrec.backend.RecipeAPI.query.get_recipe_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;

//Cú pháp: Command<Kiểu_dl_trả_về_front-end>
//Phần bên dưới chứa đầu vào của Command.
public class GetRecipeByIdQuery implements Command<RUDRecipeDTO>{
    private final String recipeid;
    public GetRecipeByIdQuery(String recipeid){
        this.recipeid = recipeid;
    }

    public String getRecipeid() {
        return recipeid;
    }
}
