package com.blogApp.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponse {

	String fileName;
	String message;
	
	public FileResponse(String fileName, String message) {
		super();
		this.fileName = fileName;
		this.message = message;
	}
	
	
}
