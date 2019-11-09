package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.buildersearch.BuildingSearchBuilder;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.impl.BuildingRepository;
import com.laptrinhjavaweb.service.IBuildingService;

public class BuildingService extends SimpleService<BuildingRepository, BuildingEntity, BuildingSearchBuilder, BuildingDTO> 
implements IBuildingService{
}
