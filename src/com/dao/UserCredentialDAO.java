package com.dao;

import java.util.List;

import com.orm.UserCredential;

public interface UserCredentialDAO
{
	public String getPassword(String id);
	public boolean authenticate(String id, String password);
	public boolean updatePassword(String userId,String password);
	public List<UserCredential> getUserCredentialList();
}
