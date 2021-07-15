package com.dao;

import com.orm.UserDetail;

public interface UserDetailDAO
{
	public String getFullName(String userID);
	public String getFirstName(String userID);
	public String getLastName(String userID);
	public Boolean isAdmin(String userID);
	public UserDetail getUserDetail(String userID);
	public boolean updateDetail(String userID,String fName, String lName, String emailID);
}
