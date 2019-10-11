package com.laptrinhjavaweb.utils;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class FormUtil {
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static <T> T toModel(Class<T> clazz, HttpServletRequest request) {
		T object = null;
		try {
			object = clazz.newInstance();
			BeanUtils.populate(object, request.getParameterMap());
			Field field[] = object.getClass().getDeclaredFields();
			for(Field x : field)
			{
				x.setAccessible(true);
				if(x.get(object) instanceof Integer || x.get(object) instanceof Long)
				{
					if(!StringUtils.isNotBlank((String) request.getParameter(x.getName())))
						x.set(object,null);
				}
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return object;
	}
}
