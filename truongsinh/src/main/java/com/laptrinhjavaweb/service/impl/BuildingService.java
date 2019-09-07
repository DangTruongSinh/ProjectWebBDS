package com.laptrinhjavaweb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.IBuildingRepository;
import com.laptrinhjavaweb.repository.impl.BuildingRepository;
import com.laptrinhjavaweb.service.IBuildingService;

public class BuildingService implements IBuildingService{
	private IBuildingRepository ibuilding = new BuildingRepository();
	private BuildingConverter convert = new BuildingConverter();
	@Override
	public List<BuildingDTO> findAll() {
		// TODO Auto-generated method stub
		List<BuildingEntity> list = ibuilding.findAll();
		return list.stream().map(x -> convert.converter(x)).collect(Collectors.toList());
	}
	
}
