package com.foodrec.backend.RecipeAPI.command.delete_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class DeleteRecipeCommandHandler implements Command.Handler<DeleteRecipeCommand,Boolean> {
    @Autowired
    private final RecipeRepository recipeRepository;
    public DeleteRecipeCommandHandler(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }
    @Override
    public Boolean handle(DeleteRecipeCommand deleteRecipeCommand){
        //B1: Kiểm tra xem Recipe đã bị xóa chưa. Nếu rồi (hidden = true thì KHỎI SET.)
        Optional<Recipe> foundRecipe = recipeRepository.findById(deleteRecipeCommand.getRecipeid());
        if(foundRecipe.get().isHidden()==true){
            return false;
        }

        recipeRepository.updateRecipeHiddenById(deleteRecipeCommand.getRecipeid());

        return true;
    }
}
