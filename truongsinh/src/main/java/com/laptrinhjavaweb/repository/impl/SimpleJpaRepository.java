package com.laptrinhjavaweb.repository.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		sql.append(createSQLForFind(map,sqlBoSung));
		if(page != null)
		{
			sql.append(" limit ");
			sql.append(page.getOffset());
			sql.append(","+page.getLimit());
		}
		return findAllBySQL(sql.toString());
	}
	protected String createSQLForFind(HashMap<String, Object> map,Object ...sqlBoSung) {
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
	public List<T> findAll(String sql,Object ...page) {
		if(page.length > 0)
			sql += " limit " + ((PageModel) page[0]).getOffset() + ","+((PageModel) page[0]).getLimit();
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
	@SuppressWarnings("deprecation")
	@Override
	public T findByID(long id) {
		String sql = "select * from "+ zClass.getAnnotation(Table.class).name()+" where id = "+id;
		List<T> list = findAllBySQL(sql);
		try {
			return list.size() > 0 ? list.get(0) : zClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	public T save(HashMap<String,Object> map) {
		String sql = createSQLForInSert(map);
		return updateOrSave(map,sql);
	}
	@Override
	public T update(HashMap<String, Object> map) {
		String sql = createSQLForUpDate(map);
		return updateOrSave(map,sql);
	}
	private T updateOrSave(HashMap<String, Object> map, String sql) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		connection = EntityManagerFactory.getConnection();
		long id = 0;
		try {
			connection.setAutoCommit(false);
			if(sql.contains("insert"))
				statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			else
				statement = connection.prepareStatement(sql);
			setParameter(statement,map);
			statement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
			if(sql.contains("insert"))
			{
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					id = resultSet.getLong(1);
				}
			}
			else
				id = (long) map.get("id");
			return findByID(id);
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if (connection != null)
					connection.close();
				if (statement != null)
					statement.close();
				if (resultSet != null)
					resultSet.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
		
	}
	private void setParameter(PreparedStatement statement, HashMap<String, Object> map) {
		int i = 1;
		long id = 0;
		try {
			for(Map.Entry<String, Object> entry : map.entrySet())
			{	
				if(!entry.getKey().equalsIgnoreCase("id"))
				{
					statement.setObject(i, entry.getValue());
					i++;
				}
				else
					id = (long) entry.getValue();	
			}
				statement.setObject(i, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private String createSQLForInSert(Map<String,Object> map) {
		StringBuilder sql = new StringBuilder();
		if(map.size() > 0)
		{
			sql.append("insert into "+ zClass.getAnnotation(Table.class).name());
			ArrayList<String> keys = new ArrayList<String>(map.keySet());
			keys.remove("id");
			sql.append("(");
			sql.append(keys.get(0));
			for(int i = 1;i < keys.size();i++)
			{
				sql.append(",");
				sql.append(keys.get(i));
			}
			sql.append(") values(?");
			for(int i = 1;i < keys.size();i++)
			{
				sql.append(",?");
			}
			sql.append(")");
		}
		return sql.toString();
	}
	
	private String createSQLForUpDate(HashMap<String, Object> map) {
		StringBuilder sql = new StringBuilder();
		if(map.size() > 0)
		{
			sql.append("update "+ zClass.getAnnotation(Table.class).name());
			ArrayList<String> keys = new ArrayList<String>(map.keySet());
			keys.remove("id");
			sql.append(" set ");
			sql.append(keys.get(0));
			sql.append("= ?");
			for(int i = 1;i < keys.size();i++)
			{
				sql.append(",");
				sql.append(keys.get(i));
				sql.append(" =?");
			}
			sql.append(" where id = ?");
		}
		return sql.toString();
	}
	@Override
	public boolean delete(Long id) {
		if(zClass.isAnnotationPresent(Table.class))
		{
			Connection connect = EntityManagerFactory.getConnection();
			if(connect != null)
			{
				PreparedStatement stament = null;
				String sql = "delete from " + zClass.getAnnotation(Table.class).name() + " where id = ?";
				try {
					stament = connect.prepareStatement(sql);
					stament.setLong(1, id);
					long sl = stament.executeUpdate();
					if(sl > 0)
						return true;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				finally {
					try {
						stament.close();
						connect.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		return false;
	}
	
}
