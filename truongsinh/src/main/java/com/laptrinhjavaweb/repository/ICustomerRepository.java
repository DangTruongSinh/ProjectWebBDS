package com.laptrinhjavaweb.repository;

import java.util.HashMap;
import java.util.List;

import com.laptrinhjavaweb.buildersearch.CustomerSearchBuilder;
import com.laptrinhjavaweb.entity.CustomerEntity;
import com.laptrinhjavaweb.page.PageModel;

public interface ICustomerRepository extends JpaRepository<CustomerEntity>{
	List<CustomerEntity> findAll(HashMap<String, Object> map,PageModel page,CustomerSearchBuilder searchBuilder);
	List<CustomerEntity> findAll(HashMap<String, Object> map,CustomerSearchBuilder searchBuilder);
}
