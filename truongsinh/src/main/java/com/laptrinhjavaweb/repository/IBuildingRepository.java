package com.laptrinhjavaweb.repository;

import java.util.HashMap;
import java.util.List;

import com.laptrinhjavaweb.buildersearch.BuildingSearchBuilder;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.page.PageModel;

public interface IBuildingRepository extends JpaRepository<BuildingEntity>{
	List<BuildingEntity> findAll(HashMap<String, Object> map,PageModel page,BuildingSearchBuilder searchBuilder);
	List<BuildingEntity> findAll(HashMap<String, Object> map,BuildingSearchBuilder searchBuilder);
}
