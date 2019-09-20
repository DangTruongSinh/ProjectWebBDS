package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.page.PageModel;

public interface IUserService{
	List<UserDTO> findAll(PageModel x);
}
