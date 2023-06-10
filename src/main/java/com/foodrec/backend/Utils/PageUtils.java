package com.foodrec.backend.Utils;

import org.springframework.stereotype.Component;

@Component
public class PageUtils {
    public boolean isNumber(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
