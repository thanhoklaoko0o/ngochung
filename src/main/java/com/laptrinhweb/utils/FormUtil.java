package com.laptrinhweb.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class FormUtil {
	
	public static <T> T toModel(Class<T> clazz,HttpServletRequest request) {
		T object = null;
		try {
			object = clazz.newInstance();
			BeanUtils.populate(object, request.getParameterMap());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

}
