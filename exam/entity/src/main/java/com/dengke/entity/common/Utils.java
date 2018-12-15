package com.dengke.entity.common;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;

public class Utils {

    private static DecimalFormat df = new DecimalFormat("#0.00");

    public static boolean existsBlank(String... strs){
        for(String s:strs){
            if(StringUtils.isBlank(s)){
                return true;
            }
        }
        return false;
    }

    public static String generateDetailScore(double... scores){
        StringBuilder sb = new StringBuilder();
        for(double score:scores){
            sb.append(df.format(score)).append(Constants.REPORT_SCORE_SPLIT);
        }
        return sb.toString();
    }
}
