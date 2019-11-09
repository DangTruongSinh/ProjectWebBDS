package com.laptrinhjavaweb.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
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

	@Override
	public DTO save(DTO dto) {
		return upDateORSave(dto,"save");
		
	}
	@Override
	public DTO update(DTO dto) {
		return upDateORSave(dto,"update");
	}
	@SuppressWarnings({ "deprecation", "unchecked" })
	private DTO upDateORSave(DTO dto, String method) {
		HashMap<String, Object> map = Convert.objectToMap(dto);
		if(method.equalsIgnoreCase("save"))
			map.put("createdDate",LocalDateTime.now());
		else
			map.put("modifiedDate",LocalDateTime.now());
		Respository respository;
		try {
			respository = classRespository.newInstance();
			Entity entity = classEntity.newInstance();
			Method methodSaveOrUpdate = classRespository.getMethod(method,HashMap.class);
			entity = (Entity) methodSaveOrUpdate.invoke(respository,map);
			return Convert.entityToDTO(entity, classDTO);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public String delete(DTO dto) {
		Respository respository;
		try {
			respository = classRespository.newInstance();
			Method methodSaveOrUpdate = classRespository.getMethod("delete",Long.class);
			Field field = classDTO.getDeclaredField("id");
			field.setAccessible(true);
			long id = (long) field.get(dto);
			boolean result = (boolean) methodSaveOrUpdate.invoke(respository,id);
			if(result)
				return "{\"success\":true}";
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"success\":false}";
	}

	
}
