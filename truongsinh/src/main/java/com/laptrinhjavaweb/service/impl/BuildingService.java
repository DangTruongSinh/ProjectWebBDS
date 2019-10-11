package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.buildersearch.BuildingSearchBuilder;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.impl.BuildingRepository;
import com.laptrinhjavaweb.service.IBuildingService;

public class BuildingService extends SimpleService<BuildingRepository, BuildingEntity, BuildingSearchBuilder, BuildingDTO> 
implements IBuildingService{
/*	private IBuildingRepository ibuilding = new BuildingRepository();
	@Override
	public List<BuildingDTO> findAll(BuildingSearchBuilder building,PageModel page) {
		HashMap<String,Object> map = Convert.objectToMap(building);
		List<BuildingEntity> list = ibuilding.findAll(map,page,building);
		return list.stream().map(x ->Convert.entityToDTO(x,BuildingDTO.class))
				.collect(Collectors.toList());
	}*/
/*	@Override
	public List<BuildingDTO> findAll(BuildingSearchBuilder building) {
		HashMap<String,Object> map = Convert.objectToMap(building);
		List<BuildingEntity> list = ibuilding.findAll(map,building);
		return list.stream().map(x ->Convert.entityToDTO(x,BuildingDTO.class))
				.collect(Collectors.toList());
	}*/
}
