package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.buildersearch.CustomerSearchBuilder;
import com.laptrinhjavaweb.dto.CustomerDTO;
import com.laptrinhjavaweb.entity.CustomerEntity;
import com.laptrinhjavaweb.repository.impl.CustomerRepository;
import com.laptrinhjavaweb.service.ICustomerService;

public class CustomerService extends SimpleService<CustomerRepository, CustomerEntity, CustomerSearchBuilder, CustomerDTO>
implements ICustomerService{
	
}
