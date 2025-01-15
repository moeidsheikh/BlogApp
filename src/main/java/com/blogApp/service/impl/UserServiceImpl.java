package com.blogApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApp.entity.User;
import com.blogApp.exception.ResourceNotFoundException;
import com.blogApp.payloads.UserDto;
import com.blogApp.repository.UserRepo;
import com.blogApp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
//		return userDto;
	}

	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}
	
	@Override
	public UserDto updateUser(UserDto userDto, int id) {
		User existingUser =  userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "ID", id));
		existingUser.setName(userDto.getName());
		existingUser.setEmail(userDto.getEmail());
		existingUser.setPassword(userDto.getPassword());
		existingUser.setAbout(userDto.getAbout());
		User updatedUser = userRepo.save(existingUser);
		UserDto userToDto = this.userToDto(updatedUser);
		return userToDto;
	}

	@Override
	public UserDto getUserById(int id) {
		User user =  userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "ID", id));
		UserDto userToDto = this.userToDto(user);
		return userToDto;
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> all = userRepo.findAll();
		List<UserDto> userToDtos = all.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userToDtos;
	}

	@Override
	public void deleteUser(int id) {
		User user =  userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "ID", id));
		this.userRepo.delete(user);
	}

}
