package com.blogApp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogApp.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//File name
		String name = file.getOriginalFilename();
		
		//random file name
		String randomId = UUID.randomUUID().toString();
		String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));
		
		
		//file path
		String filePath = path + File.separator + fileName;
		
		//create folder if not exists
		File f = new File(path);
		if(!f.exists())
		{
			f.mkdir();
		}
		
		//copy file
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName;
	}

	@Override
	public InputStream getImage(String path, String fileName) throws FileNotFoundException {
		String fullPath = path+File.separator+fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
	}

}
