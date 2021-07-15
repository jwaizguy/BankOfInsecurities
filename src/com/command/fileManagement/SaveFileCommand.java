package com.command.fileManagement;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.command.PermanentCommand;
import com.constants.ControllerConstants;

public class SaveFileCommand implements PermanentCommand {
	
	private String directory;
	private MultipartFile multipartFile;
	private final String filename;
	private static Logger logger = Logger.getLogger(SaveFileCommand.class);
	private ServletContext servlet;
	
	public SaveFileCommand(String directory, MultipartFile multipartFile, ServletContext servlet) {
		this.directory = directory;
		this.multipartFile = multipartFile;
		this.filename = this.multipartFile.getOriginalFilename();
		this.servlet = servlet;
	}
	
	public SaveFileCommand(String directory, MultipartFile multipartFile, String filename, ServletContext servlet) {
		this.directory = directory;
		this.multipartFile = multipartFile;
		this.filename = filename;
		this.servlet = servlet;
	}

	@Override
	public void execute() { 
		String realPathToUploads = servlet.getRealPath(ControllerConstants.DEFAULT_SERVLET_UPLOAD_DIRECTORY);
		
		if(! new File(realPathToUploads).exists()) {
			new File(realPathToUploads).mkdir();
		}
		
		this.directory = realPathToUploads;
		String filePath = realPathToUploads + this.filename;
		File dest = new File(filePath);
		
		try {
			this.multipartFile.transferTo(dest);
			logger.info("Saved to: " + filePath);
		} catch (IllegalStateException | IOException e) {
			logger.info(e.toString());
		}
		
		/*if(! this.multipartFile.isEmpty()) {
			String fullpath = this.directory + this.filename;
			File dest = new File(fullpath);
			try {
				this.multipartFile.transferTo(dest);
			} catch (IllegalStateException | IOException e) {
				logger.info(e.toString());
			}
		}*/
	}
	
}
