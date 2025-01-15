package com.blogApp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.blogApp.payloads.PostDto;
import com.blogApp.payloads.PostResponse;

public interface PostService {

	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	PostDto createPostWithImage(PostDto postDto, MultipartFile file, Integer userID, Integer categoryId) throws IOException;

	public PostDto updatePost(PostDto postDto, Integer postId);
	
	public void deletePost(Integer postId);
	
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	public PostDto getPostById(Integer postId);
	
	public PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);
	
	public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);
	
	public List<PostDto> searchPosts(String keyword);
}
