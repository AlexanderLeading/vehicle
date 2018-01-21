package com.alexander.v.tbox.utils;

import com.google.gson.Gson;

public class JsonUtils {
	private static final Gson gson = new Gson();

	public static String toJSONString(Object object) {
		return gson.toJson(object);
	}

	public static Object JSONToObject(String json, Class beanClass) {
		Gson gson = new Gson();
		Object res = gson.fromJson(json, beanClass);
		return res;
	}
}
