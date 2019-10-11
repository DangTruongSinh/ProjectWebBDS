package com.laptrinhjavaweb.repository.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
	public List<T> findAll(HashMap<String, Object> map,PageModel page,Object ...sqlBoSung) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ");
		sql.append(zClass.getAnnotation(Table.class).name());
		sql.append(" A where 1 = 1 ");
		sql.append(createSQL(map,sqlBoSung));
		if(page != null)
		{
			sql.append(" limit ");
			sql.append(page.getOffset());
			sql.append(","+page.getLimit());
		}
		return findAllBySQL(sql.toString());
	}
	protected String createSQL(HashMap<String, Object> map,Object ...sqlBoSung) {
		StringBuilder sql = new StringBuilder();
		String s ="";
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			
		    if(entry.getValue() instanceof String && StringUtils.isNotBlank((String) entry.getValue()))
		    {
		    	s = " and LOWER("+entry.getKey()+") like \"%" + entry.getValue() + "%\" ";
		    	sql.append(s);
		    }
		    else if((entry.getValue() instanceof Integer 
		    		|| entry.getValue() instanceof Long ||  
		    		entry.getValue() instanceof Boolean) && entry.getValue() != null)
		    {
		    	s = " and LOWER("+entry.getKey()+") =" + entry.getValue();
		    	sql.append(s);
		    }
		    
		    	
		}  
		if(sqlBoSung.length > 0)
			sql.append(sqlBoSung[0]);
		
		
		return sql.toString();
	}
	@Override
	public List<T> findAll(HashMap<String, Object> map, Object... sql) {
		return findAll(map,null,sql);
	}
	@Override
	public List<T> findAll(String sql,PageModel page) {
		if(page != null)
			sql += " limit " + page.getOffset() + ","+page.getLimit();
		return findAllBySQL(sql);
	}
	private List<T> findAllBySQL(String sql)
	{
		List<T> list = new ArrayList<T>();
		if(zClass.isAnnotationPresent(Table.class))
		{
			Connection connect = EntityManagerFactory.getConnection();
			if(connect != null)
			{
				PreparedStatement stament = null;
				ResultSet resultSet = null;
				try {
					stament = connect.prepareStatement(sql);
					resultSet = stament.executeQuery();
					list = new ResultsetMapper<T>().mapRow(resultSet, zClass);
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
		return list;
	}
	
}
