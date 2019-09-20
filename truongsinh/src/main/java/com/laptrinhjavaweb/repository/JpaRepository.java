package com.laptrinhjavaweb.repository;

import java.util.HashMap;
import java.util.List;

import com.laptrinhjavaweb.page.PageModel;

public interface JpaRepository<T> {
	List<T> findAll(HashMap<String, Object> map,PageModel page);
}
