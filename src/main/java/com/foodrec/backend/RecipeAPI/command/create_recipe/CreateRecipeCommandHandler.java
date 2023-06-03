package com.foodrec.backend.RecipeAPI.command.create_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Config.ModelMapperConfig;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.CheckEmptyFields;
import com.foodrec.backend.Utils.RecipeIdGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CreateRecipeCommandHandler implements Command.Handler<CreateRecipeCommand,Boolean>{

    @Autowired
    private final RecipeRepository recipeRepository;
    private final RecipeIdGenerator recipeIdGenerator;
    private ModelMapper modelMapper;
    private final CheckEmptyFields checkEmptyFields;
    private final ArrayList<String> excludedFields = new ArrayList<>
            (Arrays.asList("description"));

    public CreateRecipeCommandHandler(RecipeRepository recipeRepository,
                                      RecipeIdGenerator recipeIdGenerator,
                                      CheckEmptyFields checkEmptyFields,
                                      ModelMapper modelMapper ) {
        this.recipeRepository = recipeRepository;
        this.recipeIdGenerator = recipeIdGenerator;
        this.checkEmptyFields = checkEmptyFields;
        this.modelMapper = modelMapper;
    }

    public Boolean handle(CreateRecipeCommand createRecipeCommand){
        try{
            //B1: Kiểm tra xem các thuộc tính trong công thức (v.d. tên,...) có bị null không.
            boolean containsAnyEmptyField =
                    checkEmptyFields.containsAnyEmptyField(createRecipeCommand.getRudRecipeDTO()
                            ,excludedFields);
            if (containsAnyEmptyField==true){
                return false;
            }

            //B2: Nếu Ok thì map từ Dto sang Entity và thêm vào database.
            Recipe recEntity = modelMapper.map(createRecipeCommand.getRudRecipeDTO(),Recipe.class);

            recEntity.setRecipeid(recipeIdGenerator.generateRecId());
            recEntity.setUserid("U0001");
            recEntity.setHidden(false);
            recipeRepository.save(recEntity);


            //B3: Kiểm tra xem công thức đã được add chưa, bằng cách tìm lại chính công thức đó.
            Optional<Recipe> isAddedRec = recipeRepository.findById(recEntity.getRecipeid());
            if(isAddedRec.get()==null){
                return false;
            }

        }catch(IllegalAccessException e){

        }

        return true;

    }
}
