package com.blogApp.service;

import java.util.List;

import com.blogApp.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto, int id);
	
	UserDto getUserById(int id);
	
	List<UserDto> getAllUser();
	
	void deleteUser(int id);
}
