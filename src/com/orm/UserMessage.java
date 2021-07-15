package com.orm;
// object-relation mapping between User Message to the User Message in the postgre Database
import com.controller.models.MessageToAdminModel;
import com.utility.UtilityFunctions;

public class UserMessage
{
	private String msgID;
	private String subject;
	private String msg;
	private String sender;
	private String fileName;
	
	public UserMessage() { }
	
	public UserMessage(MessageToAdminModel messageToAdminModel) {
		this.msgID = UtilityFunctions.generateRandomUUID();
		this.subject = messageToAdminModel.getSubject();
		this.msg = messageToAdminModel.getMessage();
		this.sender = messageToAdminModel.getSentBy();
		this.fileName = messageToAdminModel.getAttachdFilename();
	}
	
	public String getMsgID() {
		return msgID;
	}
	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
