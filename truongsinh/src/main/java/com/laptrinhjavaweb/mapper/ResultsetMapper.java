package com.laptrinhjavaweb.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.laptrinhjavaweb.anotation.Column;


public class ResultsetMapper<T> {
	public List<T> mapRow(ResultSet resultSet,Class<?> zClass)
	{
		try {
			ResultSetMetaData metadata = resultSet.getMetaData();
			Field[] fields = zClass.getDeclaredFields();
			List<T> listResult = new ArrayList<T>();
			while(resultSet.next())
			{
				@SuppressWarnings({ "unchecked", "deprecation" })
				T object = (T) zClass.newInstance();
				for(int i = 0; i < metadata.getColumnCount(); i++)
				{
					String columnName = metadata.getColumnName(i+1);
					Object columnValue = resultSet.getObject(i+1);
					for(Field field : fields)
					{
						if(field.isAnnotationPresent(Column.class))
						{
							Column column = field.getAnnotation(Column.class);
							if(column.name().equals(columnName))
							{
								if(field.isAnnotationPresent(Column.class) && columnValue != null)
								{
									BeanUtils.setProperty(object, field.getName(), columnValue);
								}
							}
						}
						
					}
				}
				listResult.add(object);
				
			}
			return listResult;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
