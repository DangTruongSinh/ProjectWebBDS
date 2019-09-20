package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.builder.BuilderBuilding;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.page.PageModel;

public interface IBuildingService {
	List<BuildingDTO> findAll(BuilderBuilding building,PageModel x);
}
