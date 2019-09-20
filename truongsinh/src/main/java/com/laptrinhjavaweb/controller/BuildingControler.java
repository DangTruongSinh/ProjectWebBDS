package com.laptrinhjavaweb.controller;

import java.util.List;

import com.laptrinhjavaweb.builder.BuilderBuilding;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.page.PageModel;
import com.laptrinhjavaweb.service.IBuildingService;
import com.laptrinhjavaweb.service.impl.BuildingService;

public class BuildingControler {
	
	public static void main(String[] args) {
		
		IBuildingService iBuildingService = new BuildingService();
		PageModel page = new PageModel(2, 1);
		BuilderBuilding building = new BuilderBuilding.Builder()
//									.setName("FCL Buiding")
//									.setNumberofbasement(2)
//									.setBuildingarea(500)
//									.setDistrict("QUAN_1")
									.setType("TANG_TRIET")
									.build();
		List<BuildingDTO> buildingDTOs =  iBuildingService.findAll(building,page);
		for (BuildingDTO item : buildingDTOs) {
			System.out.println("Name: " + item.getName() 
			+ "   " + "NumberOfBasement:" + item.getNumberOfBasement()
			+ "   " + "BuildingArea:"  + item.getBuildingArea()
			+ "   " + "District:" + item.getDistrict()
			+ "   " + "Ward:" + item.getWard()
			+ "   " + "Createby:" + item.getCreatedBy());
	}
}
}
