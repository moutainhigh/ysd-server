package com.qmd.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ModelToMap {


	/**
	 * 将javaBean转换成Map
	 * 
	 * @param javaBean
	 *            javaBean
	 * @return Map对象
	 */
	public static Map<String, Object> model2Map(Object javaBean) {
		Map<String, Object> result = new HashMap<String, Object>();

		// 获取父类参数
		Class<?> superClazz = javaBean.getClass().getSuperclass();
		while (superClazz != Object.class) {
			Method[] methods = javaBean.getClass().getSuperclass()
					.getDeclaredMethods();

			for (Method method : methods) {
				try {
					if (method.getName().startsWith("get")) {
						String field = method.getName();
						field = field.substring(field.indexOf("get") + 3);
						field = field.toLowerCase().charAt(0)
								+ field.substring(1);

						Object value = method.invoke(javaBean, (Object[]) null);
						result.put(field, null == value ? null : value);
					}
				} catch (Exception e) {
				}
			}

			superClazz = superClazz.getClass().getSuperclass();

		}

		Method[] methods = javaBean.getClass().getDeclaredMethods();

		for (Method method : methods) {
			try {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);

					Object value = method.invoke(javaBean, (Object[]) null);
					result.put(field, null == value ? null : value);
				}
			} catch (Exception e) {
			}
		}

		return result;
	}

}
