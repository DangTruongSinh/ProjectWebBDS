package com.laptrinhjavaweb.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.laptrinhjavaweb.anotation.Special;
import com.laptrinhjavaweb.page.PageModel;
import com.laptrinhjavaweb.service.AbstractService;
import com.laptrinhjavaweb.utils.Convert;
/**
 * This class contains common functions for service 
 * @author sinh
 *
 * @param <Respository> is Class of one kind Respository
 * @param <Entity> is Class of one kind Entity
 * @param <SearchBuilder> is Class of one kind SearchBuilder
 * @param <DTO> is Class of one kind DTO
 */
public class SimpleService<Respository, Entity, SearchBuilder, DTO> implements AbstractService<SearchBuilder, DTO> {
	Class<Respository> classRespository;
	Class<SearchBuilder> classSearchBuilder;
	Class<Entity> classEntity;
	Class<DTO> classDTO;

	@SuppressWarnings("unchecked")
	public SimpleService() {

		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		classRespository = (Class<Respository>) parameterizedType.getActualTypeArguments()[0];
		classEntity = (Class<Entity>) parameterizedType.getActualTypeArguments()[1];
		classSearchBuilder = (Class<SearchBuilder>) parameterizedType.getActualTypeArguments()[2];
		classDTO = (Class<DTO>) parameterizedType.getActualTypeArguments()[3];
	}

	@Override
	public List<DTO> findAll(SearchBuilder builder, PageModel page) {
		return commonFind(builder, page);
	}

	@Override
	public List<DTO> findAll(SearchBuilder builder) {

		return commonFind(builder);
	}

	@SuppressWarnings("unchecked")
	private List<DTO> commonFind(SearchBuilder builder, Object... page) {
		try {
			@SuppressWarnings("deprecation")
			Respository respository = classRespository.newInstance();
			HashMap<String, Object> map = Convert.objectToMap(builder);
			Method findAll;
			List<Entity> list = null;
			Field[] fields = classSearchBuilder.getDeclaredFields();
			/*
			 * check class SearchBuilder if it has one special field then pass classSearchBuilder to method getMethod
			 */
			boolean flag = false;
			for(Field x : fields)
			{
				if(x.isAnnotationPresent(Special.class))
				{
					flag = true;
					break;
				}
			}
			if(flag)
			{
				if (page.length > 0) {
					findAll = classRespository.getMethod("findAll", HashMap.class, PageModel.class, classSearchBuilder);
					list = (List<Entity>) findAll.invoke(respository, map, page[0], builder);
				} else {
					findAll = classRespository.getMethod("findAll", HashMap.class, classSearchBuilder);
					list = (List<Entity>) findAll.invoke(respository, map, builder);
				}
			}
			else
			{
				if (page.length > 0) {
					findAll = classRespository.getMethod("findAll", HashMap.class, PageModel.class, Object[].class);
					list = (List<Entity>) findAll.invoke(respository, map, page[0],new Object[]{});
				} else {
					findAll = classRespository.getMethod("findAll", HashMap.class, Object[].class);
					list = (List<Entity>) findAll.invoke(respository, map,new Object[]{});
				}
			}
			return list.stream().map(x -> Convert.entityToDTO(x, classDTO)).collect(Collectors.toList());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
