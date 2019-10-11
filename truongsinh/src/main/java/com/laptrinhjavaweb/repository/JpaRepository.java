package com.laptrinhjavaweb.repository;

import java.util.HashMap;
import java.util.List;

import com.laptrinhjavaweb.page.PageModel;

public interface JpaRepository<T> {
	List<T> findAll(HashMap<String, Object> map,PageModel page,Object ...sql);
	List<T> findAll(HashMap<String, Object> map,Object ...sql);
	List<T> findAll(String sql,PageModel page);
}
