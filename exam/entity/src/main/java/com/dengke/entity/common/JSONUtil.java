package com.dengke.entity.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


public class JSONUtil {

	/**
	 * 将Object类型的对象转换成JSON
	 */
	public static String fromObject(Object obj) {
		return JSON.toJSONString(obj, SerializerFeature.WriteNullStringAsEmpty);
	}



}
