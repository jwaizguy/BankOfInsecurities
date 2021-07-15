package com.command.fileManagement;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.command.PermanentCommand;
import com.constants.ControllerConstants;

public class LoadUploadedFileCommand implements PermanentCommand {
	
	private String filename;
	private File outputFile;
	private ServletContext servlet;
	private static Logger logger = Logger.getLogger(FileToMultipartFileCommand.class);
	
	public LoadUploadedFileCommand(String filename, ServletContext servlet) {
		this.filename = filename;
		this.servlet = servlet;
	}
	
	
	public LoadUploadedFileCommand(String filename) {
		this.filename = filename;
		this.servlet = null;
	}
	
	@Override
	public void execute() {
		if(this.servlet == null) {
			ClassLoader classLoader = getClass().getClassLoader();
			this.outputFile = new File(classLoader.getResource(ControllerConstants.UPLOADED_FILES_DIRECTORY + this.filename).getFile());
			if(this.outputFile.exists()) {
				logger.info("it is real");
			}
		} else {
			String path = this.servlet.getRealPath(ControllerConstants.DEFAULT_SERVLET_UPLOAD_DIRECTORY);
			this.outputFile = new File(path + this.filename);
		}
	}
	
	public File getOutputFile() {
		return this.outputFile;
	}
	
	public MultipartFile getOutputMultipartFile() {
		FileToMultipartFileCommand fileConverter = new FileToMultipartFileCommand(this.outputFile);
		fileConverter.execute();
		return fileConverter.getOutput();
	}

}
