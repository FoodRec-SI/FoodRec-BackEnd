package com.foodrec.backend.Utils;

import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;

@Component
public class CheckEmptyFields {
    @Autowired
    private RecipeRepository recipeRepository;
    public CheckEmptyFields(){}
    public boolean containsAnyEmptyField(RecipeDTO rec, ArrayList<String> exceptionList)throws IllegalAccessException{
        //lấy danh sách các fields (thuộc tính có sẵn trong object Recipe rec)
        //getFields không hoạt động vì tất cả thuộc tính của Recipe class là PRIVATE.
        //Vì vậy phải sử dụng hàm getDeclaredFields.
        Field[] fields = rec.getClass().getDeclaredFields();
        for (Field eachField:fields){
            eachField.setAccessible(true); //bắt buộc phải có. Nếu không thì nó sẽ không lấy giá trị của từng field được (v.d. giá trị của recipename là "Bánh xèo thơm ngon").
            String eachFieldName = eachField.getName();
            if(exceptionList.contains(eachFieldName)==false){ //nếu tên của mỗi eachField không nằm trong danh sách ngoại lệ, thì mới check.
                Object eachFieldValue = eachField.get(rec);
                if(eachFieldValue == null){ //chỉ cần 1 field bị trống là cút (isAnyEmptyField = true)
                    return true;
                }
            }
        }
        return false;
    }
}
