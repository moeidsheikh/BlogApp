package com.blogApp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogApp.entity.Category;
import com.blogApp.entity.Post;
import com.blogApp.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	Page<Post> findPostByCategory(Category category, Pageable p);
	
	Page<Post> findPostByUser(User user, Pageable p);
	
	@Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
	List<Post> findByTitleOrContentContaining(String keyword);

}
