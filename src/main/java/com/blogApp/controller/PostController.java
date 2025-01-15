package com.blogApp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogApp.payloads.ApiResponse;
import com.blogApp.payloads.PostDto;
import com.blogApp.payloads.PostResponse;
import com.blogApp.service.FileService;
import com.blogApp.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired 
	PostService postService;
	
	@Autowired
	FileService fileService;
	@Value("${file}")
	String path;
	
	
	//create post with image
	@PostMapping("/user/{userId}/category/{categoryId}/posts/post-with-image")
	public ResponseEntity<PostDto> createPostWithImage(
	    @RequestParam("image") MultipartFile file,
	    @RequestParam String title, 
	    @RequestParam String content,
	    @PathVariable Integer userId,@PathVariable Integer categoryId
	) throws IOException {
		
	    PostDto postDto = new PostDto();
	    postDto.setTitle(title);
	    postDto.setContent(content);

	    PostDto post = this.postService.createPostWithImage(postDto, file,userId,categoryId);
	    return new ResponseEntity<>(post, HttpStatus.CREATED);
	}

	
	//create post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto, 
			@PathVariable Integer userId,
			@PathVariable Integer categoryId) throws IOException{
		 if (userId == null || categoryId == null) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
	        }
		PostDto post = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(post,HttpStatus.CREATED);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(
			@PathVariable int categoryId,
			@RequestParam(value = "page_Number", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "page_Size", defaultValue = "10", required = false) Integer pageSize){
		PostResponse postByCategory = postService.getPostByCategory(categoryId,pageNumber,pageSize);
		return new ResponseEntity<PostResponse>(postByCategory,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostByUser(
			@PathVariable int userId,
			@RequestParam(value = "page_Number", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "page_Size", defaultValue = "10", required = false) Integer pageSize){
		PostResponse postByUser = postService.getPostByUser(userId,pageNumber,pageSize);
		return new ResponseEntity<PostResponse>(postByUser,HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber, 
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "sortBy" , defaultValue = "postId" , required = false)String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc" , required = false)String sortDir){
		PostResponse allPost = postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK); 
	}
	
	@GetMapping("/posts/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int id){
		PostDto postById = postService.getPostById(id);
		return new ResponseEntity<PostDto>(postById,HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable int postId) {
		postService.deletePost(postId);
		return new ApiResponse("Post is deleted successfully",true);	
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatPost(@RequestBody PostDto postDto, @PathVariable int postId){
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
		
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keyword){
		List<PostDto> searchPosts = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(searchPosts, HttpStatus.OK);
	}
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> fileUpload(
			@RequestParam("image") MultipartFile file,
			@PathVariable int postId
			) throws IOException{
		
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, file);
		postDto.setImage(fileName); 
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
				
	}
	
	
} 
