package com.dao;

import java.util.List;

import com.orm.Account;

public interface AccountDAO
{
	public void modifyBalance(long accountID, double amount);
	public Account getAccountByID(long accountID);
	public List<Account> getAccountsByUserID(String userID);
	public String getUserID(long accountID);
	public boolean doesAccountIDExist(long accountID);
}
