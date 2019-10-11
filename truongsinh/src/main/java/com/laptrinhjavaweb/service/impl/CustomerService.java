package com.laptrinhjavaweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.laptrinhjavaweb.buildersearch.CustomerSearchBuilder;
import com.laptrinhjavaweb.dto.CustomerDTO;
import com.laptrinhjavaweb.entity.CustomerEntity;
import com.laptrinhjavaweb.page.PageModel;
import com.laptrinhjavaweb.repository.ICustomerRepository;
import com.laptrinhjavaweb.repository.impl.CustomerRepository;
import com.laptrinhjavaweb.service.ICustomerService;
import com.laptrinhjavaweb.utils.Convert;

public class CustomerService implements ICustomerService{
	private ICustomerRepository icustomer = new CustomerRepository();
	@Override
	public List<CustomerDTO> findAll(CustomerSearchBuilder customer, PageModel page) {
		HashMap<String,Object> map = Convert.objectToMap(customer);
		List<CustomerEntity> list = icustomer.findAll(map,page);
		return list.stream().map(x ->Convert.entityToDTO(x,CustomerDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<CustomerDTO> findAll(CustomerSearchBuilder customer) {
		HashMap<String,Object> map = Convert.objectToMap(customer);
		List<CustomerEntity> list = icustomer.findAll(map);
		return list.stream().map(x ->Convert.entityToDTO(x,CustomerDTO.class))
				.collect(Collectors.toList());
	}

}
