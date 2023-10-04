package com.sad.t5t1.project.services;

import org.springframework.web.multipart.MultipartFile;

import com.sad.t5t1.project.model.DatabaseFileT1;

public interface iDatabaseFileServiceT1 {
    public DatabaseFileT1 storeFile(MultipartFile file);
	
	public Boolean delete(String id);
	
	public DatabaseFileT1 getFile(String fileId);
}
