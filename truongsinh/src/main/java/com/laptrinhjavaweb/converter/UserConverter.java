package com.laptrinhjavaweb.converter;

import org.modelmapper.ModelMapper;

import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.entity.UserEntity;

public class UserConverter {
	public UserDTO converter(UserEntity userEntity)
	{
		ModelMapper mapper = new ModelMapper();
		return mapper.map(userEntity, UserDTO.class);
	}

}
