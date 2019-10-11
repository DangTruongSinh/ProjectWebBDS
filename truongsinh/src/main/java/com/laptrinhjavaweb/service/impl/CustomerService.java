package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.buildersearch.CustomerSearchBuilder;
import com.laptrinhjavaweb.dto.CustomerDTO;
import com.laptrinhjavaweb.entity.CustomerEntity;
import com.laptrinhjavaweb.repository.impl.CustomerRepository;
import com.laptrinhjavaweb.service.ICustomerService;

public class CustomerService extends SimpleService<CustomerRepository, CustomerEntity, CustomerSearchBuilder, CustomerDTO>
implements ICustomerService{
	// Trong lớp SimpleService cung cấp 2 hàm chung. 
	/*private ICustomerRepository icustomer = new CustomerRepository();
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
*/
}
