package com.sad.t5t1.project.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sad.t5t1.project.exception.FileNotFoundExceptionT1;
import com.sad.t5t1.project.exception.FileStorageExceptionT1;
import com.sad.t5t1.project.model.DatabaseFileT1;
import com.sad.t5t1.project.repository.DatabaseFileRepository;

@Service("mainDatabaseFileServiceT1")
public class DatabaseFileServiceT1 implements iDatabaseFileServiceT1{
    @Autowired
    private DatabaseFileRepository dbFileRepository;

    public DatabaseFileT1 storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileStorageExceptionT1("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFileT1 dbFile = new DatabaseFileT1(fileName, file.getContentType(), file.getBytes());
            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageExceptionT1("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
	public Boolean delete(String id) {
		Optional<DatabaseFileT1> foundfile= dbFileRepository.findById(id);
		
		if(foundfile.isEmpty()==true) {
			return false;
        }
		
		dbFileRepository.delete(foundfile.get());
		return true;
	}

    public DatabaseFileT1 getFile(String fileId) {
        return dbFileRepository
        		.findById(fileId)
                .orElseThrow(() -> new FileNotFoundExceptionT1("File not found with id " + fileId));
    }
}
