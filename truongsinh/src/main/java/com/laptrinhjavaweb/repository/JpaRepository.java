package com.laptrinhjavaweb.repository;

import java.util.HashMap;
import java.util.List;

import com.laptrinhjavaweb.page.PageModel;

public interface JpaRepository<T> {
	List<T> findAll(HashMap<String, Object> map,PageModel page,Object ...sql);
	List<T> findAll(HashMap<String, Object> map,Object ...sql);
	List<T> findAll(String sql,Object ...page);
	T findByID(long id);
	T save(HashMap<String,Object> map);
	T update(HashMap<String,Object> map);
	boolean delete(Long id);
}
