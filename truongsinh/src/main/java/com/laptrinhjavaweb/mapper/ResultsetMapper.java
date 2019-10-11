package com.laptrinhjavaweb.mapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.laptrinhjavaweb.anotation.Column;


public class ResultsetMapper<T> {
	private boolean flag;
	public List<T> mapRow(ResultSet resultSet,Class<?> zClass)
	{
		List<T> listResult = new ArrayList<T>();
		try {
			ResultSetMetaData metadata = resultSet.getMetaData();
			Field[] fields = zClass.getDeclaredFields();
			
			DataOfColumn columData = new DataOfColumn(); 
			while(resultSet.next())
			{
				@SuppressWarnings({ "unchecked", "deprecation" })
				T object = (T) zClass.newInstance();
				for(int i = 0; i < metadata.getColumnCount(); i++)
				{
					flag = false;
					String columnName = metadata.getColumnName(i+1);
					Object columnValue = resultSet.getObject(i+1);
					columData.setColumnName(columnName);
					columData.setColumnValue(columnValue);
					mapDataToColumn(fields,columData,object);
					Class<?> parentT = zClass.getSuperclass();
					while(parentT != null)
					{
						Field[] fieldsParent = parentT.getDeclaredFields();
						mapDataToColumn(fieldsParent,columData,object);
						parentT = parentT.getSuperclass();
					}
					
				}
				listResult.add(object);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listResult;
	}
	private void mapDataToColumn(Field fields[],DataOfColumn colum,Object object)
	{
		try {
			if(!flag)
			{
				for(Field field : fields)
				{
					if(field.isAnnotationPresent(Column.class))
					{
						Column column = field.getAnnotation(Column.class);
						if(column.name().equals(colum.getColumnName()))
						{
							if(field.isAnnotationPresent(Column.class) && colum.getColumnValue() != null)
							{
								BeanUtils.setProperty(object, field.getName(), colum.getColumnValue());
								flag = true;
								break;
							}
						}
					}
					
				}
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	 private class DataOfColumn
	{
		private String columnName;
		private Object columnValue;
		public DataOfColumn() {
			
		}
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		public Object getColumnValue() {
			return columnValue;
		}
		public void setColumnValue(Object columnValue) {
			this.columnValue = columnValue;
		}
		
	}
}
