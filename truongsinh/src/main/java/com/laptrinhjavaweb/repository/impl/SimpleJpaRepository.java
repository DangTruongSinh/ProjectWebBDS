package com.laptrinhjavaweb.repository.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.anotation.Table;
import com.laptrinhjavaweb.mapper.ResultsetMapper;
import com.laptrinhjavaweb.page.PageModel;
import com.laptrinhjavaweb.repository.JpaRepository;


public class SimpleJpaRepository<T> implements JpaRepository<T>{
	private Class<T> zClass;
	@SuppressWarnings("unchecked")
	public SimpleJpaRepository()
	{
		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		zClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}
	@Override
	public List<T> findAll(HashMap<String, Object> map,PageModel page) {
		if(zClass.isAnnotationPresent(Table.class))
		{
			Connection connect = EntityManagerFactory.getConnection();
			if(connect != null)
			{
				PreparedStatement stament = null;
				ResultSet resultSet = null;
				try {
					String sql = createSql(zClass,map,page);
					stament = connect.prepareStatement(sql);
					resultSet = stament.executeQuery();
					return new ResultsetMapper<T>().mapRow(resultSet, zClass);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				finally {
					try {
						resultSet.close();
						stament.close();
						connect.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		return null;
	}
	private String createSql(Class<T> zClass2, HashMap<String, Object> map, PageModel page) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ");
		sql.append(zClass.getAnnotation(Table.class).name());
		sql.append(" where 1 = 1 ");
		String s ="";
		for (Map.Entry<String, Object> entry : map.entrySet()) {
		    if(entry.getValue() instanceof String && !entry.getValue().equals(""))
		    	s = " and LOWER("+entry.getKey()+") like \"%" + entry.getValue() + "%\" ";
		    else if((entry.getValue() instanceof Integer 
		    		|| entry.getValue() instanceof Long ||  
		    		entry.getValue() instanceof Boolean) && entry.getValue() != null)
		    	s = " and LOWER("+entry.getKey()+") =" + entry.getValue();
		    sql.append(s);
		}  

		
		sql.append(" limit ");
		sql.append(page.getOffset());
		sql.append(","+page.getLimit());
		return sql.toString();
	}
	
	
}
