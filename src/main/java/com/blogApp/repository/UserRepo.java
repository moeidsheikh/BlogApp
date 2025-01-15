package com.blogApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApp.entity.User;




public interface UserRepo extends JpaRepository<User, Integer> {

	 Optional<User> findById(Integer id);
	 
	 Optional<User> findByEmail(String email);	 
}
