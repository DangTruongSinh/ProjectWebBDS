package com.laptrinhjavaweb.controller;

import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.page.PageModel;
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.service.impl.UserService;

public class UserController {
	public static void main(String[] args) {
		IUserService userService = new UserService();
		PageModel page = new PageModel(2, 1);
		for(UserDTO x : userService.findAll(page))
		{
			System.out.println(x.getUserName() + " - "+ x.getFullName());
		}
	}
}
