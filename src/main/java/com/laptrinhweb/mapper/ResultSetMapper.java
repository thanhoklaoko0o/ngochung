package com.laptrinhweb.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.laptrinhweb.annotation.Column;
import com.laptrinhweb.annotation.Entity;

public class ResultSetMapper<T> {
	// tra ve list khi query
	public List<T> mapRow(ResultSet rs, Class zClass) {
		List<T> results = new ArrayList<>();
		try {
			
			// kiem tra zclass co annotation
			if (zClass.isAnnotationPresent(Entity.class)) {
				
				// access vao database
				ResultSetMetaData resultSetMetaData = rs.getMetaData();
				
				// Field reflect get tat ca nhung field trong zclass => get name va value cua
				// field
				Field[] fields = zClass.getDeclaredFields();
				while (rs.next()) {					
					// convert zclass qua doi tuong
					T object = (T) zClass.newInstance();					
					// get gia tri cau 1 row trong resultset set vao torng entity
					for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {						
						// lay ten field
						String columnName = resultSetMetaData.getColumnName(i + 1);
						Object columnValue = rs.getObject(i + 1);
						// current class	
						convertResultSetToEntity(fields,columnName,columnValue,object);
						// parent class
						Class<?> parentClass=zClass.getSuperclass();
						while(parentClass !=null) {
							Field[] fieldsParent = parentClass.getDeclaredFields();
							//logic convert data
							
							convertResultSetToEntity(fieldsParent,columnName,columnValue,object);
							parentClass=parentClass.getSuperclass();
						}
					}
					results.add(object);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	private void convertResultSetToEntity(Field[] fields, String columnName, Object columnValue, T object) throws IllegalAccessException, InvocationTargetException {
		for (Field field : fields) {
			if (field.isAnnotationPresent(Column.class)) {								
				// get comlum co annotation do ra								
				Column column = field.getAnnotation(Column.class);
				if (column.name().equals(columnName) && columnValue != null) {								
					// set gia tri vao T
					BeanUtils.setProperty(object, field.getName(), columnValue);
					break;
				}
			}
		}
	}
}

/* hàm convet

for (Field field : fieldsParent) {
	if (field.isAnnotationPresent(Column.class)) {								
		// get comlum co annotation do ra								
		Column column = field.getAnnotation(Column.class);
		if (column.name().equals(columnName) && columnValue != null) {								
			// set gia tri vao T
			BeanUtils.setProperty(object, field.getName(), columnValue);
			break;
		}
	}
}

*/
