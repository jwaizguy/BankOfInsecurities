package com.dao;

import java.util.List;

import com.orm.UserMessage;

public interface MessageToAdminDAO 
{
	public void saveMessage(UserMessage message);
	public List<UserMessage> getAllMessages();
	public UserMessage getUserMessageByID(String msgID);
	public void deleteUserMessage(String msgID);
}
