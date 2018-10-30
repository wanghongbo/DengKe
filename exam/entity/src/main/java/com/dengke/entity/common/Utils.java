package com.dengke.entity.common;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Utils {

    public static boolean existsBlank(String... strs){
        for(String s:strs){
            if(StringUtils.isBlank(s)){
                return true;
            }
        }
        return false;
    }
}
