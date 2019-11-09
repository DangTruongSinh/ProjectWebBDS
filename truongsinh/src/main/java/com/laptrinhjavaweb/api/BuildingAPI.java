package com.laptrinhjavaweb.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.buildersearch.BuildingSearchBuilder;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.page.PageModel;
import com.laptrinhjavaweb.service.IBuildingService;
import com.laptrinhjavaweb.service.impl.BuildingService;
import com.laptrinhjavaweb.utils.FormUtil;
import com.laptrinhjavaweb.utils.HttpUtil;

@WebServlet(urlPatterns = { "/api-building" })
public class BuildingAPI extends HttpServlet {
	IBuildingService iBuildingService = new BuildingService();
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		BuildingDTO buildingDTO = HttpUtil.of(req.getReader()).toModel(BuildingDTO.class);
		mapper.writeValue(resp.getOutputStream(), iBuildingService.update(buildingDTO));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		BuildingDTO buildingDTO = HttpUtil.of(req.getReader()).toModel(BuildingDTO.class);
		mapper.writeValue(resp.getOutputStream(), iBuildingService.save(buildingDTO));
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		List<BuildingDTO> buildingDTOs = null;
		PageModel page = null;
		ObjectMapper mapper = new ObjectMapper();
		BuildingDTO buildingDTO = FormUtil.toModel(BuildingDTO.class, req);
		BuildingSearchBuilder building = new BuildingSearchBuilder.Builder()
				.setName(buildingDTO.getName())
				.setNumberofbasement(buildingDTO.getNumberOfBasement())
				.setBuildingarea(buildingDTO.getBuildingArea())
				.setDistrict(buildingDTO.getDistrict())
				.setCostRentfrom(buildingDTO.getCostRentFrom())
				.setCostRentTo(buildingDTO.getCostRentTo())
				.setAreaRentFrom(buildingDTO.getAreaRentFrom())
				.setAreaRentTo(buildingDTO.getAreaRentTo())
				.setBuilddingType(buildingDTO.getBuilddingType())
				.setStreet(buildingDTO.getStreet())
				.setWard(buildingDTO.getWard())
				.setStaffid(buildingDTO.getStaffid())
				.build();
		try
		{
			page = new PageModel(Integer.parseInt(buildingDTO.getPage())
					,Integer.parseInt(buildingDTO.getLimit()));
			buildingDTOs =  iBuildingService.findAll(building,page);
		}catch (Exception e) {
			buildingDTOs =  iBuildingService.findAll(building);
		}
		mapper.writeValue(resp.getOutputStream(), buildingDTOs);
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		BuildingDTO buildingDTO = HttpUtil.of(req.getReader()).toModel(BuildingDTO.class);
		JsonNode actualObj = mapper.readTree(iBuildingService.delete(buildingDTO));
		mapper.writeValue(resp.getOutputStream(), actualObj);
	}
}
