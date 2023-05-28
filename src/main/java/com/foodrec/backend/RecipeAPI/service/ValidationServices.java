package com.foodrec.backend.RecipeAPI.service;

import com.foodrec.backend.RecipeAPI.dto.RecipeDto;
import com.foodrec.backend.RecipeAPI.model.Recipe;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ValidationServices {
    public static boolean containsAnyEmptyField(RecipeDto rec, ArrayList<String> exceptionList)throws IllegalAccessException{
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
