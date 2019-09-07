package com.laptrinhjavaweb.repository.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.laptrinhjavaweb.anotation.Table;
import com.laptrinhjavaweb.mapper.ResultsetMapper;
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
	public List<T> findAll() {
		if(zClass.isAnnotationPresent(Table.class))
		{
			Connection connect = EntityManagerFactory.getConnection();
			if(connect != null)
			{
				PreparedStatement stament = null;
				ResultSet resultSet = null;
				try {
					String sql = "select * from "+ zClass.getAnnotation(Table.class).name();
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
	
}