package com.foodrec.backend.RecipeAPI.command.create_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.ReadRecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.exceptions.InvalidRecipeAttributeException;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.RecipeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CreateRecipeCommandHandler implements Command.Handler<CreateRecipeCommand,ReadRecipeDTO>{

    @Autowired
    private final RecipeRepository recipeRepository;
    private ModelMapper modelMapper;
    private final RecipeUtils recipeUtils;
    private final ArrayList<String> nonNullFields = new ArrayList<>
            (Arrays.asList("recipeName","description","image"));
    private final ArrayList<String> nonNegativeFields = new ArrayList<>
            (Arrays.asList("calories","duration"));

    public CreateRecipeCommandHandler(RecipeRepository recipeRepository,
                                      RecipeUtils recipeUtils,
                                      ModelMapper modelMapper ) {
        this.recipeRepository = recipeRepository;
        this.recipeUtils = recipeUtils;
        this.modelMapper = modelMapper;
    }

    public ReadRecipeDTO handle(CreateRecipeCommand createRecipeCommand)
    throws InvalidRecipeAttributeException {
        ReadRecipeDTO result = null;

            //B1: Kiểm tra xem các thuộc tính trong công thức (v.d. tên,...) có hợp lý ko
            // (v.d. tên khác null, số calories lớn hơn 0)
            boolean isValid =
                    recipeUtils.fieldValidator(createRecipeCommand.getCreateRecipeDTO(),
                            nonNullFields,nonNegativeFields);
            if (isValid==false){
                throw new InvalidRecipeAttributeException ("One of the recipe attributes " +
                        "(name,description, calories, duration, image)" +
                        " is invalid. Please try again.");
            }

            //B2: Nếu Ok thì map từ Dto sang Entity và thêm vào database.
            Recipe recEntity = modelMapper.map(createRecipeCommand.getCreateRecipeDTO(),Recipe.class);

            recEntity.setRecipeId(recipeUtils.generateRecId());
            recEntity.setUsername("vathuglife");//temporary username for testing
            recEntity.setStatus(true);
            recipeRepository.save(recEntity);


            //B3: Kiểm tra xem công thức đã được add chưa, bằng cách tìm lại chính công thức đó.
            Optional<Recipe> isAddedRec = recipeRepository.findById(recEntity.getRecipeId());
            if(isAddedRec.get()!=null){
                result = modelMapper.map(isAddedRec.get(),ReadRecipeDTO.class);
            }




        return result;

    }
}
