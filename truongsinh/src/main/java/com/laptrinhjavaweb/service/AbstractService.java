package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.page.PageModel;

public interface AbstractService<SearchBuilder,DTO> {
	public List<DTO> findAll(SearchBuilder builder,PageModel page);
	public List<DTO> findAll(SearchBuilder builder);
	public DTO save(DTO dto); 
	public DTO update(DTO dto);
	public String delete(DTO dto);
}
