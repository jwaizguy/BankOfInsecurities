package com.controller.models;

import javax.servlet.ServletContext;

import org.springframework.web.multipart.MultipartFile;

import com.command.fileManagement.LoadUploadedFileCommand;
import com.orm.UserMessage;

public class MessageToAdminModel {
	private String sentBy;
	private String subject;
	private String message;
	public final static String nullFileString = "<nullFile>";
	
	// This datatype is the only I can find that spring can use to upload
	private MultipartFile multipartFile;
	
	public MessageToAdminModel() { }
	
	
	// create from database
	public MessageToAdminModel(ServletContext servlet, UserMessage userMessage) {
		this.sentBy = userMessage.getSender();
		this.subject = userMessage.getSubject();
		this.message = userMessage.getMsg();
		
		// set up the file if there is one
		if(userMessage.getFileName().equals(this.nullFileString)) {
			LoadUploadedFileCommand loader = new LoadUploadedFileCommand(userMessage.getFileName(), servlet);
			loader.execute();
			this.multipartFile = loader.getOutputMultipartFile();
		}
	}
	
	public MultipartFile getMultipartFile() {
		return this.multipartFile;
	}
	
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	
	public String getAttachdFilename() {
		if(this.multipartFile == null) {
			return this.nullFileString;
		}
		return this.multipartFile.getOriginalFilename();
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
	}
	
	public String getSentBy() {
		return this.sentBy;
	}
	
	@Override
	public String toString() {
		String filename = null;
		if(this.multipartFile != null) {
			filename = this.multipartFile.getOriginalFilename();
		}
		
		return "\n "
				+ "From: " + this.nullToString(this.sentBy) + "\n "
				+ "Subject: " + this.nullToString(this.subject) + "\n "
				+ "Message: " + this.nullToString(this.message) + " \n "
				+ "FileName: " + this.nullToString(filename);
	}
	
	private String nullToString(String str) {
		if(str == null) {
			return "<null>";
		}
		return str;
	}
	
}
