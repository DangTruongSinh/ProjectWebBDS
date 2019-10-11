package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.page.PageModel;

public interface AbstractService<SearchBuilder,DTO> {
	public List<DTO> findAll(SearchBuilder builder,PageModel page);
	public List<DTO> findAll(SearchBuilder builder);
}
