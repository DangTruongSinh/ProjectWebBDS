package com.laptrinhjavaweb.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.modelmapper.ModelMapper;

import com.laptrinhjavaweb.anotation.Special;
/**
 * This is a utils converter class.
 * This convert one object entity to dto or dto to entity or object to map 
 * @author Dang Truong Sinh
 *
 */
public class Convert {
	/**
	 * This is a generic static funciton convert one entity to dto
	 * @param <Entity> is a class of entity
	 * @param <Dto> is a class of dto
	 * @param entity object of class entity need convert
	 * @param classDTO class of object dto
	 * @return One object of class DTO
	 */
	public static <Entity,Dto> Dto entityToDTO(Entity entity,Class<Dto> classDTO)
	{
		ModelMapper mapper = new ModelMapper();
		return mapper.map(entity, classDTO);
	}
	/**
	 * This is a generic static funciton convert one dto to entity
	 * @param <DTO> is a class of dto
	 * @param <Entity> is a class of entity
	 * @param dto is a object of class dto need convert
	 * @param classEntity is a class entity
	 * @return one object of class entity
	 */
	public static <DTO,Entity> Entity dtoToEntity(DTO dto,Class<Entity> classEntity)
	{
		ModelMapper mapper = new ModelMapper();
		return mapper.map(dto, classEntity);
	}
	/**
	 * This is a generic function convert one object to Map
	 * @param <T> is a class BuildersSearch
	 * @param x is a object want to convert
	 * @return a Map with type key is string and type value is object
	 */
	public static <T> HashMap<String, Object> objectToMap(T x)
	{
		HashMap<String,Object> map = new HashMap<String, Object>();
		Field[] fields = x.getClass().getDeclaredFields();
		for(Field field : fields)
		{
			field.setAccessible(true);
			if(!field.isAnnotationPresent(Special.class))
			{
				try {
					if(field.get(x) != null)
						map.put(field.getName(), field.get(x));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return map;
	}
}
