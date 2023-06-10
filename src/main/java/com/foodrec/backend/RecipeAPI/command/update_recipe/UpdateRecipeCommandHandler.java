//package com.foodrec.backend.RecipeAPI.command.update_recipe;
//
//import an.awesome.pipelinr.Command;
//import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;
//import com.foodrec.backend.RecipeAPI.entity.Recipe;
//import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
//import com.foodrec.backend.Utils.CheckEmptyFields;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Optional;
//
//@Component
//public class UpdateRecipeCommandHandler implements Command.Handler<UpdateRecipeCommand, Boolean> {
//    @Autowired
//    private final RecipeRepository recipeRepository;
//    private ModelMapper modelMapper;
//    private final CheckEmptyFields checkEmptyFields;
//    private final ArrayList<String> excludedFields = new ArrayList<>
//            (Arrays.asList("description"));
//
//    public UpdateRecipeCommandHandler(RecipeRepository recipeRepository,
//                CheckEmptyFields checkEmptyFields,ModelMapper modelMapper) {
//        this.recipeRepository = recipeRepository;
//        this.checkEmptyFields = checkEmptyFields;
//        this.modelMapper = modelMapper;
//    }
//
//    @Override
//    public Boolean handle(UpdateRecipeCommand updateRecipeCommand) {
//
//        try{
//            //B1: Kiểm tra xem các thuộc tính trong công thức (v.d. tên,...) có bị null không.
//            boolean containsAnyEmptyField =
//                    checkEmptyFields.containsAnyEmptyField(updateRecipeCommand.getRUDRecipeDTO()
//                            ,excludedFields);
//            if (containsAnyEmptyField==true){
//                return false;
//            }
//
//            //B2: Kiểm tra xem cái Recipe theo Id đó có bị xóa không.
//            //Nếu bị xóa rồi thì khỏi cập nhật.
//            Optional<Recipe> foundRecipe = recipeRepository.findById(updateRecipeCommand.getRUDRecipeDTO().getRecipeid());
//            if(foundRecipe.get().isHidden()==true){
//                return false;
//            }
//
//            //B3: Nếu Ok thì map từ Dto sang Entity và update vào database
//            //Đồng thời bổ sung thêm cái userId vào recipe entity, do bên Dto KHÔNG CÓ.
//            Recipe recEntity = modelMapper.map(updateRecipeCommand.getRUDRecipeDTO(),Recipe.class);
//            recEntity.setUserid("U0001"); //Tạm thời gài tạm userId = 1, do hiện tại chưa có cookies.
//            recEntity.setHidden(false);
//
//            recipeRepository.save(recEntity);
//
//        }catch(IllegalAccessException e){
//
//        }
//
//        return true;
//    }
//}