package com.laptrinhjavaweb.service.impl;

import java.util.List;

import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.page.PageModel;
import com.laptrinhjavaweb.service.IUserService;

public class UserService implements IUserService{
//	private IUserRepository iuser = new UserRepository();
//	private UserConverter convert = new UserConverter();
	@Override
	public List<UserDTO> findAll(PageModel page) {
		return null;
	/*	return iuser.findAll(page).stream().map(x -> convert.converter(x)).
				collect(Collectors.toList());*/
	}
	
}
