package com.blogApp.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApp.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

	Optional<Category> findById(Integer id);
}
