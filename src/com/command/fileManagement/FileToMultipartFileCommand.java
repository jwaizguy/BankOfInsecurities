package com.command.fileManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.command.PermanentCommand;

public class FileToMultipartFileCommand implements PermanentCommand {

	private File file;
	private static Logger logger = Logger.getLogger(FileToMultipartFileCommand.class);
	private MultipartFile multipartFile;
	
	public FileToMultipartFileCommand(File file) {
		this.file = file;
	}
	
	@Override
	public void execute() {
		try {
			FileInputStream input = new FileInputStream(this.file);
			this.multipartFile = new MockMultipartFile("file", this.file.getName(), "text/plain", IOUtils.toByteArray(input));
		} catch (FileNotFoundException e) {
			logger.info(e.toString());
		} catch (IOException e) {
			logger.info(e.toString());
		}
	}
	
	public MultipartFile getOutput() {
		return this.multipartFile;
	}

}
