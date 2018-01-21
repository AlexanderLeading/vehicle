package com.alexander.v.tbox.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtils {

	private static Properties props;

	public static String getProperty(String name) {
		String result = null;
		try {
			if (props == null)
				props = PropertiesLoaderUtils
						.loadAllProperties("config.properties");
			result = props.getProperty(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getRefreshProperty(String name) {
		String result = null;
		try {
			props = PropertiesLoaderUtils
					.loadAllProperties("config.properties");
			result = props.getProperty(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean getBooleanProperty(String name) {
		return "true".equalsIgnoreCase(getProperty(name));
	}

	public static void main(String[] args) {
		try {
			if (props == null)
				props = PropertiesLoaderUtils
						.loadAllProperties("config.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Enumeration<?> em = props.elements();
		while (em.hasMoreElements()) {
			Object object = em.nextElement();
			System.out.println(object);
		}
	}
}
