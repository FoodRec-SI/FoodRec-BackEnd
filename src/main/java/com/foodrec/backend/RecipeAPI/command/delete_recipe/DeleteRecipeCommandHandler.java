package com.foodrec.backend.RecipeAPI.command.delete_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.exceptions.InvalidRecipeIdException;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.RecipeUtils;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class DeleteRecipeCommandHandler implements Command.Handler<DeleteRecipeCommand,Boolean> {
    @Autowired
    private final RecipeRepository recipeRepository;
    private final RecipeUtils recipeUtils;
    public DeleteRecipeCommandHandler(RecipeRepository recipeRepository,
                                      RecipeUtils recipeUtils){
        this.recipeRepository = recipeRepository;
        this.recipeUtils = recipeUtils;
    }
    @Override
    public Boolean handle(DeleteRecipeCommand deleteRecipeCommand) throws InvalidRecipeIdException{
        //B0: Check xem người dùng có cố tình nhập bậy cái id không. Nếu có thì đá ra luôn.
        boolean isValidRecId = recipeUtils.validateRecipeId(deleteRecipeCommand.getRecipeid());
        if(!isValidRecId)
            throw new InvalidRecipeIdException
                    ("Invalid Recipe Id detected. Please try again.");
        //B1: Kiểm tra xem Recipe đã bị xóa chưa. Nếu rồi (hidden = true thì KHỎI SET.)
        Optional<Recipe> foundRecipe = recipeRepository.findById(deleteRecipeCommand.getRecipeid());
        if(foundRecipe.get().isStatus()==false){
            return false;
        }

        recipeRepository.updateRecipeStatusById(deleteRecipeCommand.getRecipeid());

        return true;
    }
}
