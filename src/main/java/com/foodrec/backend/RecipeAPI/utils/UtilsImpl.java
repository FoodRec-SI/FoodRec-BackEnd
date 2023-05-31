package com.foodrec.backend.RecipeAPI.utils;

import com.foodrec.backend.RecipeAPI.dto.NewRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service //bắt buộc phải có, nếu không thì Spring sẽ ko biết cái này là Service -> KO THỂ TẠO DC
        //1 Instance của recipeRepository.
public class UtilsImpl implements Utils{
    @Autowired
    private RecipeRepository recipeRepository;
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
    public String generateRecId(){
        String result = null;
        //Tìm xem có tồn tại 1 recipe trong Database không.
        Recipe latestRec = recipeRepository.findAll
                (Sort.by(Sort.Direction.DESC, "recipeid")).get(0);;
        if(latestRec == null){ //TH1: KO CÓ Recipe nào trong bảng Recipe
            result = "REC0001";
        }else{ //TH2: CÓ ÍT NHẤT 1 recipe trong bảng Recipe. -> Lấy id thằng recipe mới nhất + 1
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(latestRec.getRecipeid());
            String numberSection = "";
            if(m.find()) {
                numberSection = m.group(0);
            }
            int newId = Integer.parseInt(numberSection)+1;
            if(newId<10){
                result = "REC000"+newId;
            }else if(newId<100){
                result = "REC00"+newId;
            }else {
                result = "REC0"+newId;
            }

        }
        return result;
    }
}
