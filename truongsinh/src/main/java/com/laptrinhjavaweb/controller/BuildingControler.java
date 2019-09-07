package com.laptrinhjavaweb.controller;

import java.util.List;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.service.IBuildingService;
import com.laptrinhjavaweb.service.impl.BuildingService;

public class BuildingControler {
	
	public static void main(String[] args) {
		IBuildingService iBuildingService = new BuildingService();
		List<BuildingDTO> buildingDTOs =  iBuildingService.findAll();
		for (BuildingDTO item : buildingDTOs) {
			System.out.println("Name: " + item.getName() 
			+ "   " + "NumberOfBasement:" + item.getNumberOfBasement()
			+ "   " + "BuildingArea:"  + item.getBuildingArea()
			+ "   " + "District:" + item.getDistrict()
			+ "   " + "Ward:" + item.getWard());
	}
}
}
