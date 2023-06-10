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

    public RecipeUtils() {
    }

    public boolean fieldValidator(Object any,
                                  ArrayList<String> nonNullFields,
                                  ArrayList<String> nonNegativeFields) {
        try {
            Field[] fields = any.getClass().getDeclaredFields();
            for (Field eachField : fields) {
                eachField.setAccessible(true);
                String eachFieldName = eachField.getName();
                if (nonNullFields.contains(eachFieldName)) {
                    Object value = eachField.get(any);
                    if (value == null)
                        return false;
                }
                if (nonNegativeFields.contains(eachFieldName)) {
                    int testNum = Integer.parseInt(eachField.get(any).toString());
                    if (testNum <= 0)
                        return false;
                }
            }
        } catch (IllegalAccessException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    public String generateRecId() {
        String result = null;
        Recipe latestRec = recipeRepository.findAll
                (Sort.by(Sort.Direction.DESC, "recipeId")).get(0);
        if (latestRec == null) {
            result = "REC000001";
        } else {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(latestRec.getRecipeId());
            String numberSection = "";
            if (m.find()) {
                numberSection = m.group(0);
            }
            int newId = Integer.parseInt(numberSection) + 1;
            if (newId < 10) {
                result = "REC00000" + newId;
            } else if (newId < 100) {
                result = "REC0000" + newId;
            } else {
                result = "REC000" + newId;
            }
        }
        return result;
    }

    public boolean validateRecipeId(String recId) {
        Pattern p = Pattern.compile("^REC[0-9]{6}$");
        Matcher m = p.matcher(recId);
        if (!m.find())
            return false;
        return true;
    }
}
