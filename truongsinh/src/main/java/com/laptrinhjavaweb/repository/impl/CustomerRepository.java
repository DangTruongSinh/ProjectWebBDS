package com.laptrinhjavaweb.repository.impl;

import java.util.HashMap;
import java.util.List;

import com.laptrinhjavaweb.buildersearch.CustomerSearchBuilder;
import com.laptrinhjavaweb.entity.CustomerEntity;
import com.laptrinhjavaweb.page.PageModel;
import com.laptrinhjavaweb.repository.ICustomerRepository;

public class CustomerRepository extends SimpleJpaRepository<CustomerEntity> implements ICustomerRepository{

	@Override
	public List<CustomerEntity> findAll(HashMap<String, Object> map, PageModel page,
			CustomerSearchBuilder searchBuilder) {
		return findAll(map, page);
	}

	@Override
	public List<CustomerEntity> findAll(HashMap<String, Object> map, CustomerSearchBuilder searchBuilder) {
		// TODO Auto-generated method stub
		return findAll(map);
	}
	
}
