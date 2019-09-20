package com.laptrinhjavaweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.laptrinhjavaweb.builder.BuilderBuilding;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.page.PageModel;
import com.laptrinhjavaweb.repository.IBuildingRepository;
import com.laptrinhjavaweb.repository.impl.BuildingRepository;
import com.laptrinhjavaweb.service.IBuildingService;

public class BuildingService implements IBuildingService{
	private IBuildingRepository ibuilding = new BuildingRepository();
	private BuildingConverter convert = new BuildingConverter();
	@Override
	public List<BuildingDTO> findAll(BuilderBuilding building,PageModel page) {
		// TODO Auto-generated method stub
		HashMap<String,Object> map = new HashMap<String, Object>();
//		map.put("name", building.getName());
//		map.put("district", building.getDistrict());
//		map.put("buildingArea", building.getBuildingarea());
//		map.put("numberOfBasement", building.getNumberofbasement());
		map.put("type", building.getType());
		List<BuildingEntity> list = ibuilding.findAll(map,page);
		return list.stream().map(x -> convert.converter(x)).collect(Collectors.toList());
	}
	
}
