package com.foodrec.backend.RecipeAPI.command.update_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RecipeAPI.dto.ReadRecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.exceptions.InvalidRecipeAttributeException;
import com.foodrec.backend.RecipeAPI.exceptions.InvalidRecipeIdException;
import com.foodrec.backend.RecipeAPI.exceptions.RecipeNotFoundException;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.RecipeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Component
public class UpdateRecipeCommandHandler implements Command.Handler<UpdateRecipeCommand, ReadRecipeDTO> {
    @Autowired
    private final RecipeRepository recipeRepository;

    private ModelMapper modelMapper;
    private final RecipeUtils recipeUtils;
    private final ArrayList<String> nonNullFields = new ArrayList<>
            (Arrays.asList("recipeName", "description", "image"));
    private final ArrayList<String> nonNegativeFields = new ArrayList<>
            (Arrays.asList("calories", "duration"));


    public UpdateRecipeCommandHandler(RecipeRepository recipeRepository,
                                      RecipeUtils recipeUtils, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeUtils = recipeUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReadRecipeDTO handle(UpdateRecipeCommand updateRecipeCommand)
        throws InvalidRecipeIdException, InvalidRecipeAttributeException {
        //B1: Kiểm tra xem các thuộc tính trong Recipe có bị null (v.d. name, image),
        //hoặc bé hơn 0 (calories,duration). Đồng thời khi cập nhật lại, phải kiểm tra xem
        //người dùng có cố tình nhập sai cái recipeId không.
        ReadRecipeDTO readRecipeDTO = null;
        boolean isRecipeIdValid = recipeUtils.validateRecipeId(updateRecipeCommand
                .getUpdateRecipeDTO().getRecipeId());
        if (isRecipeIdValid == false) {
            throw new InvalidRecipeIdException("The Recipe id is Invalid. Please try again.");
        }

        boolean isValid = recipeUtils.fieldValidator(updateRecipeCommand.
                getUpdateRecipeDTO(), nonNullFields, nonNegativeFields);
        if (isValid == false) {
            throw new InvalidRecipeAttributeException
                    ("One of the recipe attributes (name,description, calories, duration, image" +
                            " is invalid. Please try again.");
        }

        //B2: Kiểm tra xem cái Recipe theo Id đó có bị xóa không.
        //Nếu bị xóa rồi thì khỏi cập nhật.
        Optional<Recipe> foundRecipe = recipeRepository.findById(updateRecipeCommand.
                getUpdateRecipeDTO().getRecipeId());
        if (foundRecipe.get().isStatus() == false) {
            throw new RecipeNotFoundException("The provided RecipeId " +
                    "is not found or already deleted. Please try again.");
        }

        //B3: Nếu Ok thì map từ Dto sang Entity và update vào database
        //Đồng thời bổ sung thêm cái userId vào recipe entity, do bên Dto KHÔNG CÓ.
        Recipe recEntity = modelMapper.map(updateRecipeCommand.getUpdateRecipeDTO(), Recipe.class);
        recEntity.setUsername("vathuglife"); //Tạm thời gài tạm userId = 1, do hiện tại chưa có cookies.
        recEntity.setStatus(true);

        recipeRepository.save(recEntity);

        //B4: Trả về front-end nguyên cái công thức vừa update để cho front-end ktra.
        readRecipeDTO = modelMapper.map(recEntity,ReadRecipeDTO.class);

        return readRecipeDTO;
    }
}