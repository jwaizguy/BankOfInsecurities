package com.controller.models;

public class MessageModel {
	private String body = "a";
	private String header = "b";
	private String errorMessage = "c";
	
	public MessageModel() { }
	
	public MessageModel(String header, String body, String errorMessage) {
		this.header = header;
		this.body = body;
		this.errorMessage = errorMessage;
	}
	
	public void setBody(String message) {
		this.body = message;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getHeader() {
		return this.header;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
}
