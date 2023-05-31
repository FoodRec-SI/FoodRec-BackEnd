package com.foodrec.backend.RecipeAPI.service.impl;

import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.RecipeAPI.service.RecipeQueryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Optional;

@Service
public class RecipeQueryServiceImpl implements RecipeQueryService {
    @Autowired
    private RecipeRepository _recipeRepository;


    /*Link hướng dẫn cách xài ModelMapper:
    https://www.geeksforgeeks.org/spring-boot-map-entity-to-dto-using-modelmapper/ */
    @Autowired
    private ModelMapper _modelMapper; //1 cái class/máy giúp chuyển Entity sang Dto và ngược lại.
    @Override
    public ArrayList<RUDRecipeDTO> findAllRecipes() {
        //Danh sách các ENTITY (Model) Recipe (chứa đầy đủ thuộc tính, v.d. recipename, description, calories...)

        ArrayList<Recipe> recipeEntList = (ArrayList<Recipe>)_recipeRepository.findAllRecipes();


        //Danh sách chứa các DTO Recipe (CHỈ CHỨA 1 số thuộc tính mà front-end cần,
        // (recipename, description..) còn mấy cái ko cần (v.d. UserID), thì DẸP CMNR nó đi.
        ArrayList<RUDRecipeDTO> resultList = null;
        if(recipeEntList != null){
            resultList = new ArrayList<>();
            for(Recipe eachRecipe:recipeEntList){
                //Cách cái này hoạt động: Tìm ra những thuộc tính CÓ MẶT Ở CẢ Model VÀ Dto.
                //Thằng nào có mặt ở cả 2 (v.d. recipeid,recipename) thì mới lấy giá trị của tụi nó
                // (v.d. 1, Bún Bò...) rồi gắn vào Dto.
                RUDRecipeDTO RUDRecipeDTO = _modelMapper.map(eachRecipe, RUDRecipeDTO.class);
                resultList.add(RUDRecipeDTO);
            }

        }


        return resultList;
    }

    @Override
    public RUDRecipeDTO findRecipeByRecipeid(String recipeid) {
        Recipe recipe = _recipeRepository.findRecipeByRecipeId(recipeid);
        RUDRecipeDTO recDto = null;

        if(recipe!=null){
            recDto = _modelMapper.map(recipe, RUDRecipeDTO.class);
        }

        return recDto;
    }
}
