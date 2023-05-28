package com.foodrec.backend.RecipeAPI.service.Query;

import com.foodrec.backend.RecipeAPI.dto.RecipeDto;
import com.foodrec.backend.RecipeAPI.model.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeQueryServiceImpl implements RecipeQueryService {
    @Autowired
    private RecipeRepository _recipeRepository;


    /*Link hướng dẫn cách xài ModelMapper:
    https://www.geeksforgeeks.org/spring-boot-map-entity-to-dto-using-modelmapper/ */
    @Autowired
    private ModelMapper _modelMapper; //1 cái class/máy giúp chuyển Entity sang Dto và ngược lại.
    @Override
    public ArrayList<RecipeDto> findAllRecipes() {
        //Danh sách các ENTITY (Model) Recipe (chứa đầy đủ thuộc tính, v.d. recipename, description, calories...)
        ArrayList<Recipe> recipeEntList = (ArrayList<Recipe>)_recipeRepository.findAllRecipes();


        //Danh sách chứa các DTO Recipe (CHỈ CHỨA 1 số thuộc tính mà front-end cần,
        // (recipename, description..) còn mấy cái ko cần (v.d. UserID), thì DẸP CMNR nó đi.
        ArrayList<RecipeDto> resultList = null;
        if(recipeEntList != null){
            resultList = new ArrayList<>();
            for(Recipe eachRecipe:recipeEntList){
                //Cách cái này hoạt động: Tìm ra những thuộc tính CÓ MẶT Ở CẢ Model VÀ Dto.
                //Thằng nào có mặt ở cả 2 (v.d. recipeid,recipename) thì mới lấy giá trị của tụi nó
                // (v.d. 1, Bún Bò...) rồi gắn vào Dto.
                RecipeDto eachRecDto = _modelMapper.map(eachRecipe,RecipeDto.class);
                resultList.add(eachRecDto);
            }

        }


        return resultList;
    }

    @Override
    public RecipeDto findRecipeByRecipeid(String recipeid) {
        Recipe rec = _recipeRepository.findRecipeByRecipeid(recipeid);
        RecipeDto recDto = null;
        if(rec!=null){
            recDto = _modelMapper.map(rec,RecipeDto.class);
        }

        return recDto;
    }
}
