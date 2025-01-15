package com.blogApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApp.entity.Comment;
import com.blogApp.entity.Post;
import com.blogApp.exception.ResourceNotFoundException;
import com.blogApp.payloads.CommentDto;
import com.blogApp.repository.CommentRepo;
import com.blogApp.repository.PostRepo;
import com.blogApp.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	PostRepo postRepo;
	
	@Autowired
	CommentRepo commentRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment Id", commentId));
		this.commentRepo.delete(comment);
	}

}
