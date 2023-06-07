package com.foodrec.backend.Utils;

import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RecipeUtils {
    @Autowired
    private RecipeRepository recipeRepository;
    public RecipeUtils(){}
    public boolean fieldValidator(Object any,
                                  ArrayList<String> nonNullFields,
                                  ArrayList<String> nonNegativeFields) {
        //lấy danh sách các fields (thuộc tính có sẵn trong object Recipe rec)
        //getFields không hoạt động vì tất cả thuộc tính của Recipe class là PRIVATE.
        //Vì vậy phải sử dụng hàm getDeclaredFields.
        try {
            Field[] fields = any.getClass().getDeclaredFields();
            for (Field eachField : fields) {
                eachField.setAccessible(true); //bắt buộc phải có. Nếu không thì nó sẽ không lấy giá trị của từng field được (v.d. giá trị của recipename là "Bánh xèo thơm ngon").
                String eachFieldName = eachField.getName();

                //Nếu field là mấy cái KO DC NULL (v.d recipeName,desc,image)
                if (nonNullFields.contains(eachFieldName)){
                    Object value = eachField.get(any);
                    if(value==null)
                        return false;
                }

                //Nếu field là mấy cái KO DC BÉ HƠN 0 (v.d calories, duration)
                if (nonNegativeFields.contains(eachFieldName)) {
                    int testNum = Integer.parseInt(eachField.get(any).toString());
                    if(testNum<=0)
                        return false;
                }

            }
        }catch (IllegalAccessException e){ //Nếu 1 trong những fields k truy cập được
            return false;
        }catch (NumberFormatException e){ //Nếu ng dùng cố tình nhập calories, duration thành chữ
            return false;
        }
        return true;
    }
    public String generateRecId(){
        String result = null;
        //Tìm xem có tồn tại 1 recipe trong Database không.
        Recipe latestRec = recipeRepository.findAll
                (Sort.by(Sort.Direction.DESC, "recipeId")).get(0);;
        if(latestRec == null){ //TH1: KO CÓ Recipe nào trong bảng Recipe
            result = "REC000001";
        }else{ //TH2: CÓ ÍT NHẤT 1 recipe trong bảng Recipe. -> Lấy id thằng recipe mới nhất + 1
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(latestRec.getRecipeId());
            String numberSection = "";
            if(m.find()) {
                numberSection = m.group(0);
            }
            int newId = Integer.parseInt(numberSection)+1;
            if(newId<10){
                result = "REC00000"+newId;
            }else if(newId<100){
                result = "REC0000"+newId;
            }else {
                result = "REC000"+newId;
            }

        }
        return result;
    }
    public boolean validateRecipeId(String recId){
        Pattern p = Pattern.compile("^REC[0-9]{6}$");
        Matcher m = p.matcher(recId);
        if(!m.find())
            return false;
        return true;

    }
}
