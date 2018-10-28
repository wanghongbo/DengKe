package com.dengke.entity.common;

import java.util.LinkedHashMap;
import java.util.Map;


public class JsonObjectUtil {

    /**
     * 获得对象的JSON表示
     */
    public static String getDataJsonObject(Object data) {
        return JSONUtil.fromObject(data);
    }

    /**
     * 按正确次序传入 key,value,生成map
     * 
     * @param keyvalue
     * @return Map对象
     */
    public static Map<String, Object> buildMap(Object... keyvalue) {
        return MapUtil.buildMap(new LinkedHashMap<String, Object>(keyvalue.length / 2), keyvalue);
    }

//    /**
//     * 获得登录态验证通过的JSON
//     */
//    public static String getOnlyOkJson() {
//        return "{\"" + RtnConstants.rtn + "\":" + RtnConstants.OK + "}";
//    }
//
//    /**
//     * 获得指定的rtn的JSON 
//     */
//    public static String getOnlyRtnJson(int rtn) {
//        return "{\"" + RtnConstants.rtn + "\":" + rtn + "}";
//    }

   
    /**
     * 获得指定rtn、rtnMsg和data的JSON
     * 
     * @param rtn 返回码
     * @param rtnMsg 给前端展示提示框的文本信息
     * @param data 数据
     * @return JSON表示
     */
    public static String getRtnAndDataJsonObject(int rtn, String rtnMsg, Object data) {
        Map<String, Object> object = new LinkedHashMap<String, Object>(3);
        object.put(RtnConstants.rtn, rtn);
        object.put(RtnConstants.rtnMsg, rtnMsg);
        object.put(RtnConstants.data, data);
        return JSONUtil.fromObject(object);
    }
}
