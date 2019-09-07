package com.laptrinhjavaweb.controller;

import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.service.impl.UserService;

public class UserController {
	public static void main(String[] args) {
		IUserService userService = new UserService();
		for(UserDTO x : userService.findAll())
		{
			System.out.println(x.getUserName() + " - "+ x.getFullName());
		}
	}
}
