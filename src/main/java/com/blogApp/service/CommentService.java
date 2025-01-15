package com.blogApp.service;

import com.blogApp.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer commentId);
	
	void deleteComment(Integer commentId);
}

