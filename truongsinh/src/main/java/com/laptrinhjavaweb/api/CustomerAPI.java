package com.laptrinhjavaweb.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.buildersearch.CustomerSearchBuilder;
import com.laptrinhjavaweb.dto.CustomerDTO;
import com.laptrinhjavaweb.page.PageModel;
import com.laptrinhjavaweb.service.ICustomerService;
import com.laptrinhjavaweb.service.impl.CustomerService;
import com.laptrinhjavaweb.utils.FormUtil;

@WebServlet(urlPatterns = {"/api-customer"})
public class CustomerAPI extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private ICustomerService iCustomerService = new CustomerService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		PageModel page = null;
		ObjectMapper mapper = new ObjectMapper();
		List<CustomerDTO> customerDTOS = null;
		CustomerDTO customerDTO = FormUtil.toModel(CustomerDTO.class, req);
		CustomerSearchBuilder customer = new CustomerSearchBuilder.Builder()
										.setName(customerDTO.getName())
										.setEmail(customerDTO.getEmail())
										.setPhone(customerDTO.getPhone())
										.setStaffId(customerDTO.getStaffId())
										.build();
		
		try
		{
			page = new PageModel(Integer.parseInt(customerDTO.getPage())
					,Integer.parseInt(customerDTO.getLimit()));
			customerDTOS =  iCustomerService.findAll(customer,page);
		}catch (Exception e) {
			customerDTOS =  iCustomerService.findAll(customer);
		}
		mapper.writeValue(resp.getOutputStream(), customerDTOS);
		
	}
}
