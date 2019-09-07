package com.laptrinhjavaweb.converter;

import org.modelmapper.ModelMapper;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;

public class BuildingConverter {
	
	public BuildingDTO converter(BuildingEntity buildEntity)
	{
		ModelMapper mapper = new ModelMapper();
		return mapper.map(buildEntity, BuildingDTO.class);
	}

}
