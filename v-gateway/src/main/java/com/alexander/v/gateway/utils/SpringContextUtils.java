package com.alexander.v.gateway.utils;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtils implements DisposableBean,
		ApplicationContextAware {
	/**
	 * 上下文
	 */
	private static ApplicationContext applicationContext;

	/**
	 * Description:实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 * 
	 * @param context
	 *            上下文
	 */
	public void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	/**
	 * Description:取得存储在静态变量中的ApplicationContext.
	 * 
	 * @param
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException(
					"applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
		}
		return applicationContext;
	}

	/**
	 * Description:从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * 
	 * @param name
	 *            类名称。
	 * @param <T>
	 *            泛型
	 * @return <T> 返回bean对象。
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	@Override
	public void destroy() throws Exception {
		applicationContext = null;
	}
}
